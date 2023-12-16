package com.weiran.mission.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.pojo.dto.SeckillGoodsDTO;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.mission.manager.SeckillGoodsManager;
import com.weiran.mission.mapper.SeckillGoodsMapper;
import com.weiran.mission.pojo.entity.SeckillGoods;
import com.weiran.mission.service.SeckillGoodsService;
import com.weiran.mission.utils.POJOConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


@Slf4j
@Service
@DS("seckill")
@RequiredArgsConstructor
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

    private final SeckillGoodsManager seckillGoodsManager;
    private final RedisService redisService;
    private final SeckillGoodsMapper seckillGoodsMapper;

    @Override
    public int reduceStock(long goodsId) {
        SeckillGoods seckillGoods = seckillGoodsManager.getOne(
                Wrappers.<SeckillGoods>lambdaQuery().eq(SeckillGoods::getGoodsId, goodsId)
        );

        // 多线程并发写的时候，有并发问题，这里只读redis的库存，然后写入库中，避免并发问题。
        reduceStockCount(goodsId, seckillGoods);
        boolean flag = seckillGoodsManager.update(seckillGoods, Wrappers.<SeckillGoods>lambdaUpdate().eq(SeckillGoods::getGoodsId, goodsId));
        return (flag) ? 1 : 0;
    }

    private void reduceStockCount(long goodsId, SeckillGoods seckillGoods) {
        int preStockCount = redisService.get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class);
        seckillGoods.setStockCount(preStockCount);
    }

    @Override
    public PageInfo<SeckillGoodsDTO> findSeckill(Integer page, Integer pageSize, Long goodsId) {
        PageHelper.startPage(page, pageSize);
        List<SeckillGoodsDTO> seckillGoodsDTOList;
        if (StringUtils.isEmpty(goodsId)) {
            seckillGoodsDTOList = seckillGoodsMapper.findSeckill();
        } else {
            seckillGoodsDTOList = seckillGoodsMapper.findByGoodsIdLike(goodsId);
        }
        return new PageInfo<>(seckillGoodsDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Object> create(GoodsDTO goodsDTO) {
        try {
            SeckillGoodsDTO seckillGoodsDTO = POJOConverter.converter(goodsDTO);
            seckillGoodsMapper.addSeckillGoods(seckillGoodsDTO);
        } catch (Exception e) {
            log.error(e.toString());
            return Result.fail(ResponseEnum.GOODS_CREATE_FAIL);
        }
        return Result.success();
    }

    @Override
    public Result<Object> update(GoodsDTO goodsDTO) {
        SeckillGoodsDTO seckillGoodsDTO = POJOConverter.converter(goodsDTO);
        seckillGoodsMapper.updateSeckillGoods(seckillGoodsDTO);
        return Result.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        seckillGoodsMapper.deleteSeckillGoods(id);
    }

    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        try {
            for (String goodId : split) {
                seckillGoodsMapper.deleteSeckillGoods(Long.valueOf(goodId));
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}

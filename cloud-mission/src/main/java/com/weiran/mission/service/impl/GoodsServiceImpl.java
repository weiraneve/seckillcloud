package com.weiran.mission.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.common.enums.RedisCacheTimeEnum;
import com.weiran.common.enums.ResponseEnum;
import com.weiran.common.obj.Result;
import com.weiran.common.pojo.dto.GoodsDTO;
import com.weiran.common.redis.key.GoodsKey;
import com.weiran.common.redis.key.SeckillGoodsKey;
import com.weiran.common.redis.manager.RedisService;
import com.weiran.common.validation.CustomValidation;
import com.weiran.mission.manager.GoodsManager;
import com.weiran.mission.mapper.GoodsMapper;
import com.weiran.mission.pojo.entity.Goods;
import com.weiran.mission.pojo.vo.GoodsDetailVo;
import com.weiran.mission.service.GoodsService;
import com.weiran.mission.utils.qiniu.ImageScalaKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@DS("goods")
@RequiredArgsConstructor
public class GoodsServiceImpl implements GoodsService {

    private final ImageScalaKit imageScalaKit;
    private final GoodsManager goodsManager;
    private final GoodsMapper goodsMapper;
    private final RedisService redisService;

    /**
     * 系统初始化，把商品信息加载到Redis缓存中。后续客户访问都从缓存中读取。
     */
    @PostConstruct
    public void initGoodsInfo() {
        List<Goods> goodsList = goodsManager.list();
        for (Goods goods : goodsList) {
            redisService.set(GoodsKey.goodsKey, goods.getId().toString(),
                    goods, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        }
    }

    @Override
    public Result<List<GoodsDetailVo>> getGoodsList() {
        List<GoodsDetailVo> goodsDetailVoList = getGoodsDetailVos();
        Result<List<GoodsDetailVo>> result = new Result<>();
        result.setData(goodsDetailVoList);
        return result;
    }

    private List<GoodsDetailVo> getGoodsDetailVos() {
        List<GoodsDetailVo> goodsDetailVoList = new ArrayList<>();
        for (String goodsKey : redisService.scanKeysForPattern(GoodsKey.goodsKey.getPattern())) {
            String goodsId = goodsKey.split(GoodsKey.goodsKey.getPrefix(), 2)[1];
            Result<GoodsDetailVo> result = getGoodsDetail(Long.parseLong(goodsId));
            GoodsDetailVo goodsDetailVo = result.getData();
            if (goodsDetailVo.getGoods().getIsUsing()) {
                goodsDetailVoList.add(goodsDetailVo);
            }
        }
        return goodsDetailVoList;
    }

    // 显示商品细节。剩余时间等于0，正在秒杀中；剩余时间大于0，还没有开始秒杀；小于0，已经结束秒杀。
    @Override
    public Result<GoodsDetailVo> getGoodsDetail(long goodsId) {
        Goods goods = redisService.get(GoodsKey.goodsKey, String.valueOf(goodsId), Goods.class);
        return getGoodsDetailVoResult(goodsId, goods);
    }

    private Result<GoodsDetailVo> getGoodsDetailVoResult(long goodsId, Goods goods) {
        int stockCount = getStockCount(goodsId);
        int remainSeconds = calculateRemainSeconds(goods);
        GoodsDetailVo goodsDetailVo = createGoodsDetailVo(goods, stockCount, remainSeconds);
        return Result.success(goodsDetailVo);
    }

    private int getStockCount(long goodsId) {
        return redisService.get(SeckillGoodsKey.seckillCount, String.valueOf(goodsId), Integer.class);
    }

    private int calculateRemainSeconds(Goods goods) {
        long startTime = Timestamp.valueOf(goods.getStartTime()).getTime();
        long endTime = Timestamp.valueOf(goods.getEndTime()).getTime();
        long now = System.currentTimeMillis();

        if (now < startTime) {
            return (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            return -1;
        } else {
            return 0;
        }
    }

    private GoodsDetailVo createGoodsDetailVo(Goods goods, int stockCount, int remainSeconds) {
        return GoodsDetailVo.builder()
                .goods(goods)
                .stockCount(stockCount)
                .remainSeconds(remainSeconds)
                .build();
    }

    @Override
    public PageInfo<GoodsDTO> findGoods(Integer page, Integer pageSize, String goodsName) {
        PageHelper.startPage(page, pageSize);
        List<GoodsDTO> goodsDTOList;
        if (StringUtils.isEmpty(goodsName)) {
            goodsDTOList = goodsMapper.findGoods();
        } else {
            goodsDTOList = goodsMapper.findByGoodsNameLike(goodsName);
        }
        return new PageInfo<>(goodsDTOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(GoodsDTO goodsDTO) {
        try {
            goodsMapper.addGoods(goodsDTO);
        } catch (Exception e) {
            log.error(e.toString());
        }
        addGoodsToCache(goodsDTO);
    }

    private void addGoodsToCache(GoodsDTO goodsDTO) {
        redisService.set(GoodsKey.goodsKey, String.valueOf(goodsDTO.getId()),
                goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
        redisService.set(SeckillGoodsKey.seckillCount, String.valueOf(goodsDTO.getId()),
                goodsDTO.getGoodsStock(), RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectGoodsById(id);
        deleteGoodsImage(goodsDTO);
        redisService.delete(GoodsKey.goodsKey, String.valueOf(id));
        redisService.delete(SeckillGoodsKey.seckillCount, String.valueOf(id));
        goodsMapper.deleteGoods(id);
    }

    @Override
    public void deletes(String ids) {
        String[] split = ids.split(",");
        List<String> goodsIds = Arrays.asList(split);
        deleteGoods(goodsIds);
    }

    @Async
    void deleteGoods(List<String> goodsIds) {
        List<GoodsDTO> goodsDTOList = goodsMapper.findGoodsByIds(goodsIds);
        for (GoodsDTO goodsDTO : goodsDTOList) {
            deleteGoodsImage(goodsDTO);
            try {
                redisService.delete(GoodsKey.goodsKey, String.valueOf(goodsDTO.getId()));
                redisService.delete(SeckillGoodsKey.seckillCount, String.valueOf(goodsDTO.getId()));
                goodsMapper.deleteGoods(goodsDTO.getId());
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }

    @Override
    public void update(GoodsDTO goodsDTO) {
        goodsMapper.updateGoods(goodsDTO);
        addGoodsToCache(goodsDTO);
    }

    @Override
    public GoodsDTO selectById(Long id) {
        GoodsDTO goodsDTO = goodsMapper.selectGoodsById(id);
        CustomValidation.isInvalid(ObjectUtil.isNull(goodsDTO), ResponseEnum.RESOURCE_NOT_FOUND);
        return goodsDTO;
    }

    @Override
    public void updateUsingById(Long id) {
        goodsMapper.updateGoodsUsingById(id);
        GoodsDTO goodsDTO = redisService.get(GoodsKey.goodsKey, String.valueOf(id), GoodsDTO.class);
        goodsDTO.setIsUsing(BooleanUtil.negate(goodsDTO.getIsUsing()));
        redisService.set(GoodsKey.goodsKey, String.valueOf(id),
                goodsDTO, RedisCacheTimeEnum.GOODS_LIST_EXTIME.getValue());
    }

    private void deleteGoodsImage(GoodsDTO goodsDTO) {
        if (ObjectUtil.isNotEmpty(goodsDTO.getGoodsImg())) {
            try {
                URL url = new URL(goodsDTO.getGoodsImg());
                imageScalaKit.delete(url.getPath().replaceFirst("/", ""));
            } catch (MalformedURLException e) {
                log.error(e.toString());
            }
        }
    }

}

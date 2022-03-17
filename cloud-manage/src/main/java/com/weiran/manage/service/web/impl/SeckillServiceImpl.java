package com.weiran.manage.service.web.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.manage.dto.web.SeckillGoodsDTO;
import com.weiran.manage.mapper.web.SeckillMapper;
import com.weiran.manage.service.web.SeckillService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {

    private final SeckillMapper seckillMapper;

    // 分页查询
    @Override
    public PageInfo<SeckillGoodsDTO> findSeckill(Integer page, Integer pageSize, Long goodsId) {
        PageHelper.startPage(page, pageSize);
        List<SeckillGoodsDTO> seckillGoodsDTOList;
        if (StringUtils.isEmpty(goodsId)) {
            seckillGoodsDTOList = seckillMapper.findSeckill();
        } else {
            // 如果有字段传入，则模糊查询
            seckillGoodsDTOList = seckillMapper.findByGoodsIdLike(goodsId);
        }
        return new PageInfo<>(seckillGoodsDTOList);
    }

    // 增加秒杀商品
    @Override
    public void addSeckillGoods(SeckillGoodsDTO seckillGoodsDTO) {
        seckillMapper.add(seckillGoodsDTO);
    }

    // 删除秒杀商品
    @Override
    public void deleteSeckillGoods(Long goodsId) {
        seckillMapper.delete(goodsId);
    }

}

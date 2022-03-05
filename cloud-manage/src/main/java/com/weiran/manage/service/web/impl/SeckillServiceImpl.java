package com.weiran.manage.service.web.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weiran.manage.mapper.web.SeckillMapper;
import com.weiran.manage.entity.web.SeckillGoods;
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
    public PageInfo<SeckillGoods> findSeckill(Integer page, Integer pageSize, Long goodsId) {
        PageHelper.startPage(page, pageSize);
        List<SeckillGoods> seckillGoodsList;
        if (StringUtils.isEmpty(goodsId)) {
            seckillGoodsList = seckillMapper.findSeckill();
        } else {
            // 如果有字段传入，则模糊查询
            seckillGoodsList = seckillMapper.findByGoodsIdLike(goodsId);
        }
        return new PageInfo<>(seckillGoodsList);
    }

    // 增加秒杀商品
    @Override
    public void addSeckillGoods(SeckillGoods seckillGoods) {
        seckillMapper.add(seckillGoods);
    }

    // 删除秒杀商品
    @Override
    public void deleteSeckillGoods(Long goodsId) {
        seckillMapper.delete(goodsId);
    }

}

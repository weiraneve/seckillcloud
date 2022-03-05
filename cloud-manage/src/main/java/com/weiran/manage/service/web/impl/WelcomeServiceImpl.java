package com.weiran.manage.service.web.impl;

import com.weiran.manage.mapper.web.GoodsMapper;
import com.weiran.manage.mapper.web.OrderMapper;
import com.weiran.manage.mapper.web.SeckillMapper;
import com.weiran.manage.mapper.uaa.SiftMapper;
import com.weiran.manage.response.WelcomeVO;
import com.weiran.manage.service.web.WelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WelcomeServiceImpl implements WelcomeService {

    private final OrderMapper orderMapper;
    private final GoodsMapper goodsMapper;
    private final SeckillMapper seckillMapper;
    private final SiftMapper siftMapper;

    // 商品数、秒杀商品数、订单数、用户数
    @Override
    public WelcomeVO welcomeCount() {
        int goodsCount = goodsMapper.findGoods().size();
        int seckillCount = seckillMapper.findSeckill().size();
        int orderCount = orderMapper.findByOrder().size();
        int siftCount = siftMapper.findSift().size();
        return WelcomeVO.build(goodsCount, seckillCount, orderCount, siftCount);
    }
}

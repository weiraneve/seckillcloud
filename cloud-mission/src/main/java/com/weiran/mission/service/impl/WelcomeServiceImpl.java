package com.weiran.mission.service.impl;

import com.weiran.mission.mapper.GoodsMapper;
import com.weiran.mission.mapper.OrderMapper;
import com.weiran.mission.mapper.SeckillGoodsMapper;
import com.weiran.common.pojo.vo.WelcomeVO;
import com.weiran.mission.service.WelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WelcomeServiceImpl implements WelcomeService {

    private final OrderMapper orderMapper;
    private final GoodsMapper goodsMapper;
    private final SeckillGoodsMapper seckillGoodsMapper;

    // 商品数、秒杀商品数、订单数、用户数
    @Override
    public WelcomeVO welcomeCount() {
        int goodsCount = goodsMapper.selectList(null).size();
        int seckillCount = seckillGoodsMapper.selectList(null).size();
        int orderCount = orderMapper.selectList(null).size();
        return WelcomeVO.build(goodsCount, seckillCount, orderCount);
    }
}

package com.weiran.manage.response;

import lombok.Data;

@Data
public class WelcomeVO {

    // 商品数
    private int goodsCount;
    // 秒杀商品数
    private int seckillCount;
    // 订单数
    private int orderCount;
    // 用户数
    private int siftCount;

    public static WelcomeVO build(int goodsCount, int seckillCount, int orderCount, int siftCount) {
        WelcomeVO welcomeVO = new WelcomeVO();
        welcomeVO.setGoodsCount(goodsCount);
        welcomeVO.setSeckillCount(seckillCount);
        welcomeVO.setOrderCount(orderCount);
        welcomeVO.setSiftCount(siftCount);
        return welcomeVO;
    }
}

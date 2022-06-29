package com.weiran.common.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode()
public class WelcomeVO implements Serializable {

    // 商品数
    private int goodsCount;
    // 秒杀商品数
    private int seckillCount;
    // 订单数
    private int orderCount;

    public static WelcomeVO build(int goodsCount, int seckillCount, int orderCount) {
        WelcomeVO welcomeVO = new WelcomeVO();
        welcomeVO.setGoodsCount(goodsCount);
        welcomeVO.setSeckillCount(seckillCount);
        welcomeVO.setOrderCount(orderCount);
        return welcomeVO;
    }
}

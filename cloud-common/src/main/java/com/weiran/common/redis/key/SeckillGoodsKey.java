package com.weiran.common.redis.key;


public class SeckillGoodsKey extends BasePrefix {

    private SeckillGoodsKey(String prefix) {
        super(prefix);
    }

    public static SeckillGoodsKey seckillCount = new SeckillGoodsKey("sc");

    public static SeckillGoodsKey seckillInfo = new SeckillGoodsKey("si");
}

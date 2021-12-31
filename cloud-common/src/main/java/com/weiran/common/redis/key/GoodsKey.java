package com.weiran.common.redis.key;

public class GoodsKey extends BasePrefix{

    private GoodsKey(String prefix) {
        super(prefix);
    }

    public static GoodsKey goodsKey = new GoodsKey( "gs");
}

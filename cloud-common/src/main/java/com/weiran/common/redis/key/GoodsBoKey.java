package com.weiran.common.redis.key;

public class GoodsBoKey extends BasePrefix{

	private GoodsBoKey(String prefix) {
		super(prefix);
	}

	public static GoodsBoKey goodsKey = new GoodsBoKey( "gb");
}

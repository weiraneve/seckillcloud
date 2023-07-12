package com.weiran.common.enums;

/**
 * Redis缓存时间的枚举类
 */
public enum RedisCacheTimeEnum {

    /**
     * 用户认证token的存在时间 12小时
     */
    LOGIN_EXTIME(60 * 30 * 24),
    GOODS_LIST_EXTIME(60 * 30 * 24),

    /**
     * 1分钟
     */
    GOODS_ID_EXTIME(60),
    SECKILL_PATH_EXTIME(60),
    GOODS_INFO_EXTIME(60);

    private final int value;

    RedisCacheTimeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

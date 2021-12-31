package com.weiran.common.enums;

/**
 * Redis缓存时间的枚举类
 */
public enum RedisCacheTimeEnum {

    /**
     * redis会话中的存在时间 30分钟
     */
    REDIS_SESSION_EXTIME(60 * 30),

    /**
     * 12小时
     */
    GOODS_LIST_EXTIME(60 * 30 * 24),

    /**
     * 1分钟
     */
    GOODS_ID_EXTIME(60),

    /**
     * 1分钟
     */
    SECKILL_PATH_EXTIME(60),

    /**
     * 1分钟
     */
    GOODS_INFO_EXTIME(60);

    private int value;

    private RedisCacheTimeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

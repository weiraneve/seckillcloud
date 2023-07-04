package com.weiran.common.utils;

import cn.hutool.json.JSONUtil;

/**
 * Bean与String 互相转换的工具类
 */
public class BeanUtil {

    /**
     * bean 转 String
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == Integer.class) {
            return String.valueOf(value);
        } else if (clazz == String.class) {
            return (String)value;
        } else if (clazz == Long.class) {
            return String.valueOf(value);
        } else {
            return JSONUtil.toJsonStr(value);

        }
    }

    /**
     * string转bean
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() == 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return clazz.cast(Integer.valueOf(str));
        } else if (clazz == String.class) {
            return clazz.cast(str);
        } else if (clazz == Long.class) {
            return clazz.cast(Long.valueOf(str));
        } else {
            return JSONUtil.toBean(JSONUtil.parseObj(str), clazz);
        }
    }
}

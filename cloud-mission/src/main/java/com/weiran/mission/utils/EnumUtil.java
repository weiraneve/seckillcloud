package com.weiran.mission.utils;


import com.weiran.common.enums.BaseEnum;

public class EnumUtil {

    public static <T extends BaseEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}

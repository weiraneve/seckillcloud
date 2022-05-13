package com.weiran.common.enums;


import com.weiran.common.enums.BaseEnum;

public enum ImageDirEnum implements BaseEnum {

    /**
     * 图片类型
     */
    goods(0, "goods"),
    index(1, "index");

    private Integer code;
    private String msg;

    ImageDirEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}

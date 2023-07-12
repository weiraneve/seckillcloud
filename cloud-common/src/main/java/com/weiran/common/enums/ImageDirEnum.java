package com.weiran.common.enums;


public enum ImageDirEnum implements BaseEnum {

    goods(0, "goods"),
    index(1, "index");

    private final Integer code;
    private final String msg;

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

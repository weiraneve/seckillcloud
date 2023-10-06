package com.weiran.common.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum implements BaseEnum {

    SUCCESS(200, "success"),
    ERROR(400, "error"),
    INVALID_PAGE_OR_PAGE_SIZE(400, "分页数据有错"),
    REQUEST_ILLEGAL(403, "请求非法"),
    USER_IS_BAN_FOUND(403, "该用户已禁用"),
    REPEATED_SECKILL(403, "不能重复秒杀"),
    INTERNAL_ACCESS_ERROR(403, "只有微服务内网才可访问"),
    UNAUTHORIZED(401, "认证失败"),
    PASSWORD_ERROR(401, "密码错误"),
    USER_NO_LOGIN(401, "用户未登录"),
    OLD_PASSWORD_ERROR(401, "旧密码有误"),
    USER_PASSWORD_VALID(401, "账号密码错误"),
    TOKEN_PARSING_ERROR(401, "token解析失败"),
    FORBIDDEN(403, "没有权限"),
    RESOURCE_NOT_FOUND(404, "资源未找到"),
    USER_NOT_FOUND(404, "用户未找到"),
    MOBILE_NOT_EXIST(404, "手机号不存在"),
    ROLE_NOT_FOUND(404, "角色未曾找到"),
    ACCESS_LIMIT_REACHED(429, "访问太频繁！"),
    IMAGE_ENUM_NOT_FOUND(415, "上传的类型不正确"),
    REPEATED_REGISTER_MOBILE(409, "手机号码已注册"),
    REPEATED_REGISTER_USERNAME(409, "用户名已经被注册"),
    PERMISSION_EXIST_ERROR(409, "已经存在"),
    PERMISSION_DELETES_ERROR(409, "已被使用,请先取消关联"),
    SECKILL_OVER(410, "商品已经秒杀完毕"),
    GOODS_CREATE_FAIL(500, "goods创建失败"),
    IMAGE_UPLOAD_FAIL(500, "上传图片失败"),
    PERMISSION_CREATE_ERROR(500, "创建失败"),
    PERMISSION_UPDATE_ERROR(500, "修改失败"),
    SYSTEM_ERROR(500, "系统异常");

    private final int code;
    private final String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseEnum [code=" + code + ", msg=" + msg + "]";
    }

}

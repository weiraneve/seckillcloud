package com.weiran.common.enums;

import lombok.Getter;

/**
 * 通用枚举类型
 */
@Getter
public enum ResponseEnum implements BaseEnum {

    /**
     * 业务与登陆模块
     */
    SUCCESS(200, "success"),
    ERROR(100, "error"),

    // 服务器模块 5001XX
    SERVER_ERROR(500100, "服务端异常"),
    BIND_ERROR(500101, "参数校验异常：%s"),
    REQUEST_ILLEGAL(500102, "请求非法"),
    ACCESS_LIMIT_REACHED(500104, "访问太频繁！"),

    // 登录模块 5002XX
    SESSION_ERROR(500210, "Session不存在或者已经失效"),
    PASSWORD_EMPTY(500211, "登录密码不能为空"),
    MOBILE_EMPTY(500212, "手机号不能为空"),
    MOBILE_ERROR(500213, "手机号格式错误"),
    MOBILE_NOT_EXIST(500214, "手机号不存在"),
    PASSWORD_ERROR(500215, "密码错误"),
    USER_NO_LOGIN(500216, "用户未登录"),

    // 商品模块 5003XX
    NO_GOODS(500300, "没有该商品"),

    // 订单模块 5004XX
    ORDER_NOT_EXIST(500400, "订单不存在"),
    ORDER_WRITE_ERROR(500400, "订单写入失败"),

    // 秒杀模块 5005XX
    SECKILL_OVER(500500, "商品已经秒杀完毕"),
    REPEATED_SECKILL(500501, "不能重复秒杀"),

    // 注册模块 5006XX
    REPEATED_REGISTER_MOBILE(500600, "手机号码已注册"),
    REPEATED_REGISTER_USERNAME(500601, "用户名已经被注册"),
    REPEATED_REGISTER_IDENTITY(500602, "身份证已经被注册"),

    // 用户模块 5007XX
    No_SIFT_PASS(500700, "客户初筛未通过"),

    // 密码模块 5008XX
    OLD_PASSWORD_ERROR(500800, "旧密码有误"),
    UPDATE_PASSWORD_ERROR(500801, "更新密码失败"),

    /**
     * 管理端相关
     */
    PERMISSION_CREATE_ERROR(101,"创建失败"),
    PERMISSION_EXIST_ERROR(102,"已经存在"),
    PERMISSION_DELETES_ERROR(103,"已被使用,请先取消关联"),
    PERMISSION_UPDATE_ERROR(104,"修改失败"),

    /**
     * 登录、验证、token相关code
     */
    UNAUTHORIZED(401, "认证失败"),
    UNAUTHORIZED_TOKEN(402, "认证失败,token不正确"),
    FORBIDDEN(403, "没有权限！"),
    RESOURCE_NOT_FOUND(404, "资源未找到"),
    USER_NOT_FOUND(404, "用户未找到"),
    USER_PASSWORD_VALID(405, "账号密码错误"),
    TOKEN_PARSING_ERROR(406, "token解析失败"),
    USER_IS_BAN_FOUND(407,"该用户已禁用"),

    /**
     * 图片类异常
     */
    IMAGE_ENUM_NOT_FOUND(501, "上传的类型不正确"),
    IMAGE_UPLOAD_FAIL(502, "上传图片失败"),

    /**
     * 模型相关
     */
    Goods_CREATE_FAIL(600, "goods创建失败"),
    ;

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

package com.weiran.common.enums;

import lombok.Getter;

/**
 * 返回的枚举类型
 */
@Getter
public enum ResponseEnum implements BaseEnum {

    /**
     * 枚举类型
     */
    UNEXPECTED(1, "未知错误"),

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
    WX_LOGIN_BINDING_ERROR(407,"该用户未绑定管理账号"),
    USER_IS_BAN_FOUND(408,"该用户已禁用"),

    /**
     * 图片类异常
     */
    IMAGE_ENUM_NOT_FOUND(501, "上传的类型不正确"),
    IMAGE_UPLOAD_FAIL(502, "上传图片失败"),
    IMAGE_DELETE_FAIL(503, "删除图片失败"),
    IMAGE_VIOLATION_FAIL(504, "图片违规"),

    /**
     * 模型相关
     */
    Goods_CREATE_FAIL(600, "goods创建失败"),
    RULE_UPDATE_FAIL(601, "rule更新失败"),
    RULE_FIND_FAIL(602, "rule查询失败"),

    /**
     * 系统相关
     */
    SYSTEM_SETTING_ERROR(700,"设置失败"),
    ;

    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}

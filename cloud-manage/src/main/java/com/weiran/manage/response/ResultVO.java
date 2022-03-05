package com.weiran.manage.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.weiran.manage.enums.ResponseEnum;
import lombok.Data;

/**
 * 后台统一返回体
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> {

    private Integer code;
    private String msg;
    private T data;

    private ResultVO(T data) {
        this.code = 200;
        this.msg = "成功";
        this.data = data;
    }

    private ResultVO(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public static <T> ResultVO<T> success(T data) {
        return new ResultVO<>(data);
    }

    public static <T> ResultVO<T> success() {
        return success(null);
    }

    public static <T> ResultVO<T> fail(Integer code, String message) {
        return new ResultVO<>(code, message);
    }

    public static <T> ResultVO<T> fail(String message) {
        return new ResultVO<>(1, message);
    }

    public static <T> ResultVO<T> fail(ResponseEnum responseEnum) {
        return new ResultVO<>(responseEnum.getCode(), responseEnum.getMsg());
    }
}

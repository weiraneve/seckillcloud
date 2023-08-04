package com.weiran.common.obj;

import com.weiran.common.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int SUCCESS_CODE = ResponseEnum.SUCCESS.getCode();
    private static final int FAILURE_CODE = ResponseEnum.ERROR.getCode();
    private static final String DEFAULT_SUCCESS_MSG = ResponseEnum.SUCCESS.getMsg();

    private int code = SUCCESS_CODE;

    private String msg;

    private T data;

    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResponseEnum.SUCCESS);
    }

    public static <T> Result<T> fail(ResponseEnum responseEnum) {
        return new Result<>(responseEnum.getCode(), responseEnum.getMsg());
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(msg);
    }

    public Result() {
    }

    public Result(T data) {
        this.msg = DEFAULT_SUCCESS_MSG;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg) {
        this.code = FAILURE_CODE;
        this.msg = msg;
    }

    public Result(ResponseEnum responseEnum) {
        if (responseEnum != null) {
            this.code = responseEnum.getCode();
            this.msg = responseEnum.getMsg();
        }
    }
}

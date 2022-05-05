package com.weiran.common.obj;

import com.weiran.common.enums.CodeMsg;
import com.weiran.common.enums.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回体
 */
@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	// 状态码
	private int code = 200;

	// 返回消息
	private String msg;

	// 返回结果参数
	private T data;

	/**
	 * 验证是否返回响应成功
	 */
	public boolean isSuccess() {
		return this.code == CodeMsg.SUCCESS.getCode();
	}

	public static <T> Result<T> success(T data) {
		return new Result<>(data);
	}

	public static <T> Result<T> success() {
		return new Result<>(CodeMsg.SUCCESS);
	}

	public static <T> Result<T> error(ResponseEnum responseEnum) {
		return new Result<>(responseEnum.getCode(), responseEnum.getMsg());
	}

	public static <T> Result<T> error(CodeMsg codeMsg) {
		return new Result<>(codeMsg);
	}

	public static <T> Result<T> error(String msg) {
		return new Result<>(msg);
	}

	public static <T> Result<T> error(int code, String msg) {
		return new Result<>(code, msg);
	}

	public Result() {}

	public Result(T data) {
		this.code = CodeMsg.SUCCESS.getCode();
		this.msg = CodeMsg.SUCCESS.getMsg();
		this.data = data;
	}

	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(String msg) {
		this.code = 100;
		this.msg = msg;
	}

	public Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}

}

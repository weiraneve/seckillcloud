package com.weiran.common.obj;

import java.io.Serializable;

/**
 * 统一返回体
 */
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
		return new Result<T>(data);
	}

	public static <T> Result<T> success(CodeMsg codeMsg) {
		return new Result<T>(codeMsg);
	}

	public static <T> Result<T> error(CodeMsg codeMsg) {
		return new Result<T>(codeMsg);
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

	public Result(CodeMsg codeMsg) {
		if(codeMsg != null) {
			this.code = codeMsg.getCode();
			this.msg = codeMsg.getMsg();
		}
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}

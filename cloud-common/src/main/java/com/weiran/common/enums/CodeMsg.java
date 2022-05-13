package com.weiran.common.enums;

import lombok.Data;

/**
 * 返回信息
 */
@Data
public class CodeMsg {
	
	private int code;
	private String msg;


	public static CodeMsg SUCCESS = new CodeMsg(200, "success");
	public static CodeMsg ERROR = new CodeMsg(100, "error");

	// 服务器模块 5001XX
	public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
	public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
	public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
	public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "访问太频繁！");

	// 登录模块 5002XX
	public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
	public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
	public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
	public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
	public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
	public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
	public static CodeMsg USER_NO_LOGIN = new CodeMsg(500216, "用户未登录");

	// 商品模块 5003XX
	public static CodeMsg NO_GOODS = new CodeMsg(500300, "没有该商品");

	// 订单模块 5004XX
	public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");
	public static CodeMsg ORDER_WRITE_ERROR = new CodeMsg(500400, "订单写入失败");

	// 秒杀模块 5005XX
	public static CodeMsg SECKILL_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
	public static CodeMsg REPEATED_SECKILL = new CodeMsg(500501, "不能重复秒杀");

	// 注册模块 5006XX
	public static CodeMsg REPEATED_REGISTER_MOBILE = new CodeMsg(500600, "手机号码已注册");
	public static CodeMsg REPEATED_REGISTER_USERNAME = new CodeMsg(500601, "用户名已经被注册");
	public static CodeMsg REPEATED_REGISTER_IDENTITY = new CodeMsg(500602, "身份证已经被注册");

	// 用户模块 5007XX
	public static CodeMsg No_SIFT_PASS = new CodeMsg(500700, "客户初筛未通过");

	// 密码模块 5008XX
	public static CodeMsg OLD_PASSWORD_ERROR = new CodeMsg(500800, "旧密码有误");
	public static CodeMsg UPDATE_PASSWORD_ERROR = new CodeMsg(500801, "更新密码失败");

	public CodeMsg() {
	}
			
	public CodeMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public CodeMsg fillArgs(Object... args) {
		int code = this.code;
		String message = String.format(this.msg, args);
		return new CodeMsg(code, message);
	}

	@Override
	public String toString() {
		return "CodeMsg [code=" + code + ", msg=" + msg + "]";
	}
	
	
}

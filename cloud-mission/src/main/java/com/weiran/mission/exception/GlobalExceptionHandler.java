package com.weiran.mission.exception;

import com.weiran.common.obj.CodeMsg;
import com.weiran.common.obj.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常拦截处理
 *
 * 拦截了异常，我们可以通过该注解实现自定义异常处理
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
		e.printStackTrace();
		if (e instanceof GlobalException) {
			GlobalException ex = (GlobalException)e;
			return Result.error(ex.getCodeMsg());
		} else if (e instanceof BindException) {
			BindException ex = (BindException)e;
			List<ObjectError> errors = ex.getAllErrors();
			ObjectError error = errors.get(0);
			String msg = error.getDefaultMessage();
			// 打印堆栈信息
			log.error(String.format(msg, msg));
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
		} else {
			return Result.error(CodeMsg.SERVER_ERROR);
		}
	}
}

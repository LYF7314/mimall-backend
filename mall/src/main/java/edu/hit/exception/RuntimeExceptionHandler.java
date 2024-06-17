package edu.hit.exception;

import edu.hit.enums.ResponseEnum;
import edu.hit.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static edu.hit.enums.ResponseEnum.ERROR;

/**
 *
 * 异常拦截处理
 */
@ControllerAdvice
public class RuntimeExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
//	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseVo handle(RuntimeException e) {
		return ResponseVo.error(ERROR, e.getMessage());
	}

	@ExceptionHandler(UserLoginException.class)
	@ResponseBody
	public ResponseVo userLoginHandle() {
		return ResponseVo.error(ResponseEnum.NEED_LOGIN);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseVo notValidExceptionHandle(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		Objects.requireNonNull(bindingResult.getFieldError());
		return ResponseVo.error(ResponseEnum.PARAM_ERROR,
				bindingResult.getFieldError().getField() + " " + bindingResult.getFieldError().getDefaultMessage());
	}
}

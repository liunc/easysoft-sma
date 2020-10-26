package com.easysoft.sma;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.easysoft.lib.common.exception.BadRequestException;
import com.easysoft.lib.common.exception.BusinessException;
import com.easysoft.lib.common.exception.ConflictException;
import com.easysoft.lib.common.exception.NotFoundException;
import com.easysoft.lib.jdb.domain.dto.GeneralResponse;
import com.easysoft.lib.jdb.domain.dto.PageResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GeneralResponseHandler implements ResponseBodyAdvice<Object> {

	private Logger logger = LoggerFactory.getLogger(GeneralResponseHandler.class);

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {

		if (body instanceof GeneralResponse) {
			return body;
		}
		if (body instanceof PageResponse) {
			return body;
		}

		GeneralResponse result = new GeneralResponse();
		result.success(200, "OK", serverHttpRequest.getURI().getPath(), serverHttpRequest.getMethodValue(), body);
		return result;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public GeneralResponse defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception {
		
		logger.error("", ex);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
			status = HttpStatus.NOT_FOUND;
		}

		GeneralResponse result = new GeneralResponse();
		result.fail(status.value(), status.name(), ex.getMessage(), request.getRequestURI(), request.getMethod());
		return result;
	}

	// 验证时绑定错误
	@ResponseBody
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GeneralResponse errorHandler(HttpServletRequest request, MethodArgumentNotValidException ex) {

		List<String> errorMessage = new ArrayList<String>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			// 获取校验的信息
			errorMessage.add(error.getDefaultMessage());
		}

		GeneralResponse result = new GeneralResponse();
		result.fail(400, "Bad Request", String.join(",", errorMessage), request.getRequestURI(), request.getMethod());
		return result;
	}

	// 验证时绑定错误
	@ResponseBody
	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GeneralResponse errorHandler(HttpServletRequest request, BusinessException ex) {

		GeneralResponse result = new GeneralResponse();
		result.fail(ex.getCode(), ex.getText(), ex.getMessage(), request.getRequestURI(), request.getMethod());
		return result;
	}

	@ResponseBody
	@ExceptionHandler(value = BadRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public GeneralResponse errorHandler(HttpServletRequest request, BadRequestException ex) {

		GeneralResponse result = new GeneralResponse();
		result.fail(ex.getCode(), ex.getText(), ex.getMessage(), request.getRequestURI(), request.getMethod());
		return result;
	}

	@ResponseBody
	@ExceptionHandler(value = NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public GeneralResponse errorHandler(HttpServletRequest request, NotFoundException ex) {

		GeneralResponse result = new GeneralResponse();
		result.fail(ex.getCode(), ex.getText(), ex.getMessage(), request.getRequestURI(), request.getMethod());
		return result;
	}

	@ResponseBody
	@ExceptionHandler(value = ConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public GeneralResponse errorHandler(HttpServletRequest request, ConflictException ex) {

		GeneralResponse result = new GeneralResponse();
		result.fail(ex.getCode(), ex.getText(), ex.getMessage(), request.getRequestURI(), request.getMethod());
		return result;
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {

		return true;
	}

}
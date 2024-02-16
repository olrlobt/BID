package com.ssafy.bid.global.error.exception;

import java.util.Arrays;

import lombok.Getter;

@Getter
public class InvalidParameterException extends RuntimeException {

	private final Object[] parameterValues;

	/**
	 * 잘못된 파라미터 요청
	 * ex) id = null
	 */
	public InvalidParameterException(String message, Object... parameterValues) {
		super(message + " Parameter Value: " + Arrays.toString(parameterValues));
		this.parameterValues = parameterValues;
	}
}

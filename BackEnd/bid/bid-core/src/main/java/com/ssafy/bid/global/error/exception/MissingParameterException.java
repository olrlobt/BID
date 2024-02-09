package com.ssafy.bid.global.error.exception;

import java.util.Arrays;

import lombok.Getter;

@Getter
public class MissingParameterException extends RuntimeException {

	private final Object[] parameterValues;

	/**
	 * 필수 파라미터가 요청에 누락됨.
	 */
	public MissingParameterException(String message, Object... parameterValues) {
		super(message + " Parameter Value: " + Arrays.toString(parameterValues));
		this.parameterValues = parameterValues;
	}
}

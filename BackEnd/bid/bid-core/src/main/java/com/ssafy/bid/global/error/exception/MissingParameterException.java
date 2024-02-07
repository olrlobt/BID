package com.ssafy.bid.global.error.exception;

import lombok.Getter;

/**
 * 필수 파라미터가 요청에 누락됨.
 */

@Getter
public class MissingParameterException extends RuntimeException {

	private final Object parameterValue;

	public MissingParameterException(String message, Object parameterValue) {
		super(message + " Parameter Value: " + parameterValue.toString());
		this.parameterValue = parameterValue;

	}
}

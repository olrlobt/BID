package com.ssafy.bid.global.error.exception;

import lombok.Getter;

/**
 * 잘못된 파라미터 요청
 * ex) id = null
 */
@Getter
public class InvalidParameterException extends RuntimeException {

	private final Object parameterValue;
	public InvalidParameterException(String message, Object parameterValue) {
		super(message + " Parameter Value: " + parameterValue.toString());
		this.parameterValue = parameterValue;
	}
}

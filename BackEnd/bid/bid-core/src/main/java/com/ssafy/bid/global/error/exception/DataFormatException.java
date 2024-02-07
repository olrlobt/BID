package com.ssafy.bid.global.error.exception;

import lombok.Getter;

/**
 * 클라이언트의 요청이 잘못된 형식임.
 */
@Getter
public class DataFormatException extends RuntimeException {

	private final Object parameterValue;

	public DataFormatException(String message, Object parameterValue) {
		super(message + " Parameter Value: " + parameterValue.toString());
		this.parameterValue = parameterValue;
	}
}

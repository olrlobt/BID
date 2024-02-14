package com.ssafy.bid.global.error.exception;

import java.util.Arrays;

import lombok.Getter;

/**
 * 클라이언트의 요청이 잘못된 형식임.
 */
@Getter
public class DataFormatException extends RuntimeException {

	private final Object[] parameterValues;

	public DataFormatException(String message, Object... parameterValues) {
		super(message + " Parameter Value: " + Arrays.toString(parameterValues));
		this.parameterValues = parameterValues;
	}
}

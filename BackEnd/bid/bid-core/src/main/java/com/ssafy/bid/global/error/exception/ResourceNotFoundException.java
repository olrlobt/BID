package com.ssafy.bid.global.error.exception;

import java.util.Arrays;

public class ResourceNotFoundException extends RuntimeException {

	private final Object[] parameterValue;

	/**
	 * 요청한 리소스를 서버에서 찾을 수 없음.
	 * ex) findUserById
	 */
	public ResourceNotFoundException(String message, Object... parameterValues) {
		super(message + " Parameter Value: " + Arrays.toString(parameterValues));
		this.parameterValue = parameterValues;
	}
}

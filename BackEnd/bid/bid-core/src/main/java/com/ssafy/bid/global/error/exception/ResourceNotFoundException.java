package com.ssafy.bid.global.error.exception;

/**
 * 요청한 리소스를 서버에서 찾을 수 없음.
 * ex) findUserById
 */

public class ResourceNotFoundException extends RuntimeException {

	private final Object parameterValue;

	public ResourceNotFoundException(String message, Object parameterValue) {
		super(message + " Parameter Value: " + parameterValue.toString());
		this.parameterValue = parameterValue;
	}
}

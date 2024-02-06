package com.ssafy.bid.global.error.exception;

/**
 * 외부 서비스로부터 잘못된 응답을 받음.
 */
public class ExternalServiceException extends RuntimeException {

	public ExternalServiceException(String message) {
		super(message);
	}
}

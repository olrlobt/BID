package com.ssafy.bid.global.error.exception;

/**
 * 서버가 요청을 이해했으나, 권한 부족으로 거절함.
 * ex) student -> admin
 */
public class AuthorizationFailedException extends RuntimeException{
	public AuthorizationFailedException(String message) {
		super(message);
	}
}

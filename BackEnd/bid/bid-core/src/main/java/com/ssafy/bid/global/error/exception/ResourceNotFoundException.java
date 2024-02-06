package com.ssafy.bid.global.error.exception;

/**
 * 요청한 리소스를 서버에서 찾을 수 없음.
 * ex) findUserById
 */

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message);
	}
}

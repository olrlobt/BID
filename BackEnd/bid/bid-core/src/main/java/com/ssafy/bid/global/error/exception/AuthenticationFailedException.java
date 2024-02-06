package com.ssafy.bid.global.error.exception;

/**
 * 요청이 인증을 필요로 함.
 * ex) 로그인 실패
 */
public class AuthenticationFailedException extends RuntimeException{
	public AuthenticationFailedException(String message) {
		super(message);
	}
}

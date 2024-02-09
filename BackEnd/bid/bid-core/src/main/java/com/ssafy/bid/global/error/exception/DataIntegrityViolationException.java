package com.ssafy.bid.global.error.exception;

public class DataIntegrityViolationException extends RuntimeException {

	/**
	 * 요청이 서버의 데이터 무결성 규칙과 충돌함.
	 */
	public DataIntegrityViolationException(String message) {
		super(message);
	}
}

package com.ssafy.bid.global.error.exception;

/**
 * 요청이 애플리케이션의 비즈니스 로직에 맞지 않음.
 * ex) blocked 유저인 경우
 */
public class InvalidActionException extends RuntimeException{
	public InvalidActionException(String message) {
		super(message);
	}
}

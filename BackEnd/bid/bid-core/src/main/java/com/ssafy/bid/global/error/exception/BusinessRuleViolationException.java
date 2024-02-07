package com.ssafy.bid.global.error.exception;

public class BusinessRuleViolationException extends RuntimeException {
	public BusinessRuleViolationException(String message) {
		super(message);
	}
}

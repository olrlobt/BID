package com.ssafy.bid.global.error.exception;

import lombok.Getter;

/**
 *  요청이 서버의 현재 상태와 충돌함, 예를 들어 이미 존재하는 리소스를 생성하려고 할 때.
 *  ex) exists(user)
 */
@Getter
public class ResourceAlreadyExistsException extends RuntimeException {

	private final Object parameterValue;

	public ResourceAlreadyExistsException(String message, Object parameterValue) {
		super(message + " Parameter Value: " + parameterValue.toString());
		this.parameterValue = parameterValue;
	}
}

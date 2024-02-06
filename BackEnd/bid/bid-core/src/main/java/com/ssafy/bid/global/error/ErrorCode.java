package com.ssafy.bid.global.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	AuthenticationFailedException(HttpStatus.UNAUTHORIZED, "요청이 인증을 필요로 함. "),
	AuthorizationFailedException(HttpStatus.FORBIDDEN, "권한 부족으로 거절함. "),
	BusinessRuleViolationException(HttpStatus.BAD_REQUEST, "요청이 비즈니스 규칙을 위반함. "),
	ConfigurationException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 구성 오류. "),
	DataFormatException(HttpStatus.BAD_REQUEST, "클라이언트의 요청이 잘못된 형식임. "),
	DataIntegrityViolationException(HttpStatus.CONFLICT, "요청이 서버의 데이터 무결성 규칙과 충돌함. "),
	ExternalServiceException(HttpStatus.BAD_GATEWAY, "외부 서비스로부터 잘못된 응답을 받음. "),
	InvalidActionException(HttpStatus.BAD_REQUEST, "요청이 애플리케이션의 비즈니스 로직에 맞지 않음. "),
	InvalidParameterException(HttpStatus.BAD_REQUEST, "요청에 잘못된 파라미터가 포함됨. "),
	MissingParameterException(HttpStatus.BAD_REQUEST, "필수 파라미터가 요청에 누락됨. "),
	NetworkException(HttpStatus.SERVICE_UNAVAILABLE, "네트워크 문제. "),
	ResourceAlreadyExistsException(HttpStatus.CONFLICT, "요청이 서버의 현재 상태와 충돌함. "),
	ResourceNotFoundException(HttpStatus.NOT_FOUND, "요청한 리소스를 서버에서 찾을 수 없음. "),
	TokenValidationException(HttpStatus.FORBIDDEN, "토큰 검증 실패. "),
	UnsupportedOperationException(HttpStatus.METHOD_NOT_ALLOWED, "요청된 메서드는 서버에서 지원되지 않음. ");

	private final HttpStatus httpStatus;
	private final String message;

}
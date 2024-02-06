package com.ssafy.bid.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.bid.global.error.exception.AuthenticationFailedException;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.BusinessRuleViolationException;
import com.ssafy.bid.global.error.exception.ConfigurationException;
import com.ssafy.bid.global.error.exception.DataFormatException;
import com.ssafy.bid.global.error.exception.DataIntegrityViolationException;
import com.ssafy.bid.global.error.exception.ExternalServiceException;
import com.ssafy.bid.global.error.exception.InvalidActionException;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.MissingParameterException;
import com.ssafy.bid.global.error.exception.NetworkException;
import com.ssafy.bid.global.error.exception.ResourceAlreadyExistsException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;
import com.ssafy.bid.global.error.exception.TokenValidationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(AuthenticationFailedException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationFailedException(AuthenticationFailedException e) {
		log.error("ResourceNotFoundException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.AuthenticationFailedException);
	}

	@ExceptionHandler(AuthorizationFailedException.class)
	public ResponseEntity<ErrorResponse> handleAuthorizationFailedException(AuthorizationFailedException e) {
		log.error("AuthorizationFailedException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.AuthorizationFailedException);
	}

	@ExceptionHandler(BusinessRuleViolationException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRuleViolationException(BusinessRuleViolationException e) {
		log.error("BusinessRuleViolationException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.BusinessRuleViolationException);
	}

	@ExceptionHandler(ConfigurationException.class)
	public ResponseEntity<ErrorResponse> handleConfigurationException(ConfigurationException e) {
		log.error("ConfigurationException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.ConfigurationException);
	}

	@ExceptionHandler(DataFormatException.class)
	public ResponseEntity<ErrorResponse> handleDataFormatException(DataFormatException e) {
		log.error("DataFormatException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.DataFormatException);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		log.error("DataIntegrityViolationException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.DataIntegrityViolationException);
	}

	@ExceptionHandler(ExternalServiceException.class)
	public ResponseEntity<ErrorResponse> handleExternalServiceException(ExternalServiceException e) {
		log.error("ExternalServiceException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.ExternalServiceException);
	}

	@ExceptionHandler(InvalidActionException.class)
	public ResponseEntity<ErrorResponse> handleInvalidActionException(InvalidActionException e) {
		log.error("InvalidActionException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.InvalidActionException);
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException e) {
		log.error("InvalidParameterException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.InvalidParameterException);
	}

	@ExceptionHandler(MissingParameterException.class)
	public ResponseEntity<ErrorResponse> handleMissingParameterException(MissingParameterException e) {
		log.error("MissingParameterException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.MissingParameterException);
	}

	@ExceptionHandler(NetworkException.class)
	public ResponseEntity<ErrorResponse> handleNetworkException(NetworkException e) {
		log.error("NetworkException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.NetworkException);
	}

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
		log.error("ResourceAlreadyExistsException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.ResourceAlreadyExistsException);
	}


	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
		log.error("ResourceNotFoundException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.ResourceNotFoundException);
	}

	@ExceptionHandler(TokenValidationException.class)
	public ResponseEntity<ErrorResponse> handleTokenValidationException(TokenValidationException e) {
		log.error("TokenValidationException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.TokenValidationException);
	}

	@ExceptionHandler(UnsupportedOperationException.class)
	public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(UnsupportedOperationException e) {
		log.error("UnsupportedOperationException ", e);
		return ErrorResponse.toResponseEntity(ErrorCode.UnsupportedOperationException);
	}
}

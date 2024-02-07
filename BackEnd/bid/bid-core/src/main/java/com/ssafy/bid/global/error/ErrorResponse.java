package com.ssafy.bid.global.error;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
	private int status;
	private String errorName;

	public ErrorResponse(int status, String errorName) {
		this.status = status;
		this.errorName = errorName;
	}

	public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode e) {
		return ResponseEntity
			.status(e.getHttpStatus())
			.body(ErrorResponse.builder()
				.status(e.getHttpStatus().value())
				.errorName(e.getMessage())
				.build());
	}
}

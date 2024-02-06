package com.ssafy.bid.domain.user.security;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
	private int status;
	private String message;
	private LocalDateTime timestamp;
}

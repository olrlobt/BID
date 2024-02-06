package com.ssafy.bid.domain.user.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}

package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelAuthenticationCheckRequest {
	private String tel;
	private String code;
}

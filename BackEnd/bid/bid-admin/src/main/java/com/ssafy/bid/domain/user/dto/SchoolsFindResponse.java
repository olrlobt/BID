package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SchoolsFindResponse {
	private Integer no;
	private String name;
	private String region;
	private String code;

	public SchoolsFindResponse(
		Integer no,
		String name,
		String region,
		String code
	) {
		this.no = no;
		this.name = name;
		this.region = region;
		this.code = code;
	}
}

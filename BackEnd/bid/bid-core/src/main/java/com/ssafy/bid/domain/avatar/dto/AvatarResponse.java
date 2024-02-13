package com.ssafy.bid.domain.avatar.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AvatarResponse {

	private Integer no;
	private String name;
	private String url;

	@Builder
	public AvatarResponse(Integer no, String name, String url) {
		this.no = no;
		this.name = name;
		this.url = url;
	}
}

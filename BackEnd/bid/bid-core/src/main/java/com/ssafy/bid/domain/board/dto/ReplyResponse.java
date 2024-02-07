package com.ssafy.bid.domain.board.dto;

import lombok.Getter;

@Getter
public class ReplyResponse {

	private String content;
	private String userName;

	public ReplyResponse(String content, String userName) {
		this.content = content;
		this.userName = userName;
	}
}

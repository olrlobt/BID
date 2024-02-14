package com.ssafy.bid.domain.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ReplyResponse {

	private long replyNo;
	private String content;
	private int userNo;
	private String userName;
	private String userImgUrl;
	private LocalDateTime createAt;

	public ReplyResponse(long replyNo, String content, int userNo, String userName, String userImgUrl,
		LocalDateTime createAt) {
		this.replyNo = replyNo;
		this.content = content;
		this.userNo = userNo;
		this.userName = userName;
		this.userImgUrl = userImgUrl;
		this.createAt = createAt;
	}
}

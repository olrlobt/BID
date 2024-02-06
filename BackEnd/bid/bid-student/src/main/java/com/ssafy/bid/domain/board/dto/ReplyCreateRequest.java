package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.Reply;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyCreateRequest {

	private String content;
	private int userNo;
	private long boardNo;

	public Reply toEntity() {
		return Reply.builder()
			.content(this.content)
			.userNo(this.userNo)
			.boardNo(boardNo)
			.build();
	}

}

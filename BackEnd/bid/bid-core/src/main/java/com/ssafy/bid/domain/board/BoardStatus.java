package com.ssafy.bid.domain.board;

import lombok.Getter;

@Getter
public enum BoardStatus {

	PROGRESS("진행중"),
	COMPLETED("진행완료");

	private final String status;

	private BoardStatus(String status) {
		this.status = status;
	}
}

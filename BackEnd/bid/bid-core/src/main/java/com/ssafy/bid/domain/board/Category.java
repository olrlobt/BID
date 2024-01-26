package com.ssafy.bid.domain.board;

import lombok.Getter;

@Getter
public enum Category {

	SNACK("간식"),
	LEARNING("학습"),
	COUPON("쿠폰"),
	GAME("오락"),
	ETC("기타");

	private final String category;

	private Category(String category) {
		this.category = category;
	}
}

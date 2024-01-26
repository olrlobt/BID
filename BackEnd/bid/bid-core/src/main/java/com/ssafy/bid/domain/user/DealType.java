package com.ssafy.bid.domain.user;

import lombok.Getter;

@Getter
public enum DealType {
	SNACK("간식"),
	LEARNING("학습"),
	COUPON("쿠폰"),
	GAME("오락"),
	ETC("기타"),
	REWARD("리워드"),
	SALARY("주급"),
	SAVING("적금");

	private final String dealType;

	private DealType(String dealType) {
		this.dealType = dealType;
	}
}

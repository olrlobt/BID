package com.ssafy.bid.domain.board;

import java.util.Arrays;

import lombok.Getter;

@Getter
public enum Category {
	SNACK("간식"),
	LEARNING("학습"),
	COUPON("쿠폰"),
	GAME("오락"),
	CANNON("대포알"),
	ETC("기타");

	private final String category;

	private Category(String category) {
		this.category = category;
	}

	public static Category from(String input) {

		String inputUpperCase = input.toUpperCase();

		return Arrays.stream(Category.values())
			.filter(category -> category.toString().equals(inputUpperCase))
			.findFirst()
			.orElseThrow();
	}

}

package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;

import lombok.Getter;

@Getter
public class BoardCreateRequest {

	private String title;
	private String description;
	private Category category;
	private String goodsImgUrl;
	private int startPrice;
	private int gradePeriodNo;

	public BoardCreateRequest(String title, String description, Category category, String goodsImgUrl, int startPrice,
		int gradePeriodNo) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.goodsImgUrl = goodsImgUrl;
		this.startPrice = startPrice;
		this.gradePeriodNo = gradePeriodNo;
	}

	public Board toEntity(int userNo, int gradeNo) {
		return Board.builder()
			.title(this.title)
			.description(this.description)
			.category(this.category)
			.goodsImgUrl(this.goodsImgUrl)
			.startPrice(this.startPrice)
			.gradePeriodNo(this.gradePeriodNo)
			.userNo(userNo)
			.gradeNo(gradeNo)
			.totalPrice(0)
			.attendeeCount(0)
			.boardStatus(BoardStatus.PROGRESS)
			.build();
	}
}

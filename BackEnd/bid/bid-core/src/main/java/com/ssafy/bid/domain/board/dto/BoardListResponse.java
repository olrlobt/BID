package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;

import lombok.Data;

@Data
public class BoardListResponse {

	private long no;
	private String title;
	private BoardStatus boardStatus;
	private int displayPrice;
	private String category;
	private String goodsImgUrl;
	private String userName;
	private int gradePeriodNo;

	public BoardListResponse(long no, String title, BoardStatus boardStatus, int displayPrice, Category category,
		String goodsImgUrl, String userName, int gradePeriodNo) {
		this.no = no;
		this.title = title;
		this.boardStatus = boardStatus;
		this.displayPrice = displayPrice;
		this.category = category.getCategory();
		this.goodsImgUrl = goodsImgUrl;
		this.userName = userName;
		this.gradePeriodNo = gradePeriodNo;
	}
}

package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;

import lombok.Data;

@Data
public class BoardResponse {

	private long no;
	private String title;
	private String description;
	private int startPrice;
	private BoardStatus boardStatus;
	private int averagePrice;
	private int resultPrice;
	private String category;
	private String goodsImgUrl;
	private String userName;
	private int gradePeriodNo;

	public BoardResponse(long no, String title, String description, int startPrice, BoardStatus boardStatus,
		int averagePrice, int resultPrice, Category category, String goodsImgUrl, String userName, int gradePeriodNo) {
		this.no = no;
		this.title = title;
		this.description = description;
		this.startPrice = startPrice;
		this.boardStatus = boardStatus;
		this.averagePrice = averagePrice;
		this.resultPrice = resultPrice;
		this.category = category.getCategory();
		this.goodsImgUrl = goodsImgUrl;
		this.userName = userName;
		this.gradePeriodNo = gradePeriodNo;
	}
}

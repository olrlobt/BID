package com.ssafy.bid.domain.board.dto;

import java.time.LocalTime;

import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;

import lombok.Getter;

@Getter
public class BoardResponse {

	private long no;
	private String title;
	private String description;
	private int startPrice;
	private BoardStatus boardStatus;
	private int averagePrice;
	private int resultPrice;
	private Category category;
	private String goodsImgUrl;
	private String userName;
	private int gradePeriodNo;
	private LocalTime createdAt;

	public BoardResponse(long no, String title, BoardStatus boardStatus, int averagePrice, int resultPrice,
		String goodsImgUrl, String userName) {
		this.no = no;
		this.title = title;
		this.boardStatus = boardStatus;
		this.averagePrice = averagePrice;
		this.resultPrice = resultPrice;
		this.goodsImgUrl = goodsImgUrl;
		this.userName = userName;
	}

	public BoardResponse(long no, String title, String description, int startPrice, BoardStatus boardStatus,
		int averagePrice, int resultPrice, Category category, String goodsImgUrl, String userName, int gradePeriodNo) {
		this.no = no;
		this.title = title;
		this.description = description;
		this.startPrice = startPrice;
		this.boardStatus = boardStatus;
		this.averagePrice = averagePrice;
		this.resultPrice = resultPrice;
		this.category = category;
		this.goodsImgUrl = goodsImgUrl;
		this.userName = userName;
		this.gradePeriodNo = gradePeriodNo;
	}
}

package com.ssafy.bid.domain.board.dto;

import java.time.LocalTime;

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
}
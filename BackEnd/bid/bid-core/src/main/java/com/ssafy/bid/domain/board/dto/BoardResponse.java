package com.ssafy.bid.domain.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
public class BoardResponse {

	private long no;
	private String title;
	private String description;
	private int startPrice;
	private BoardStatus boardStatus;
	private int averagePrice;
	@Setter
	private int displayPrice;
	private int resultPrice;
	private String category;
	private String goodsImgUrl;
	private int userNo;
	private String userName;
	private String userProfileImgUrl;
	private int gradePeriodNo;
	private LocalDateTime createdAt;

	@Setter
	private List<ReplyResponse> comments;

	public BoardResponse(long no, String title, String description, int startPrice, BoardStatus boardStatus,
		int averagePrice, int resultPrice, Category category, String goodsImgUrl, int userNo, String userName,
		String userProfileImgUrl, int gradePeriodNo, LocalDateTime createdAt) {
		this.no = no;
		this.title = title;
		this.description = description;
		this.startPrice = startPrice;
		this.boardStatus = boardStatus;
		this.averagePrice = averagePrice;
		this.resultPrice = resultPrice;
		this.category = category.getCategory();
		this.goodsImgUrl = goodsImgUrl;
		this.userNo = userNo;
		this.userName = userName;
		this.userProfileImgUrl = userProfileImgUrl;
		this.gradePeriodNo = gradePeriodNo;
		this.createdAt = createdAt;
	}

}

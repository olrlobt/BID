package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardCreateRequest {

	private String title;
	private String description;
	private Category category;
	private String goodsImgUrl;
	private int startPrice;
	private int gradePeriodNo;
	private int subNo;

	@Builder
	public BoardCreateRequest(String title, String description, Category category, String goodsImgUrl, int startPrice,
		int gradePeriodNo, int subNo) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.goodsImgUrl = goodsImgUrl;
		this.startPrice = startPrice;
		this.gradePeriodNo = gradePeriodNo;
		this.subNo = subNo;
	}

	@Builder
	public BoardCreateRequest(String title, String description, Category category, String goodsImgUrl, int startPrice,
		int gradePeriodNo) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.goodsImgUrl = goodsImgUrl;
		this.startPrice = startPrice;
		this.gradePeriodNo = gradePeriodNo;
	}

	public static BoardCreateRequest createCannon() {
		return BoardCreateRequest.builder()
			.title("대포 알")
			.description("자리 뽑기에 사용하는 대포 알")
			.category(Category.CANNON)
			.goodsImgUrl("CANNON_IMG_URL")
			.startPrice(200)
			.gradePeriodNo(6)
			.build();
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
			.subNo(this.subNo)
			.boardStatus(BoardStatus.PROGRESS)
			.build();
	}
}

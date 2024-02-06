package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.Bidding;
import com.ssafy.bid.domain.board.BiddingStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BiddingCreateRequest {

	private int price;
	private int userNo;
	private int gradeNo;
	private long boardNo;

	public Bidding toEntity(){
		return Bidding.builder()
			.price(this.price)
			.userNo(this.userNo)
			.gradeNo(this.gradeNo)
			.boardNo(this.boardNo)
			.biddingStatus(BiddingStatus.BIDDING)
			.build();
	}

	private BiddingCreateRequest(int price, int userNo, int gradeNo, int boardNo) {
		this.price = price;
		this.userNo = userNo;
		this.gradeNo = gradeNo;
		this.boardNo = boardNo;
	}
}

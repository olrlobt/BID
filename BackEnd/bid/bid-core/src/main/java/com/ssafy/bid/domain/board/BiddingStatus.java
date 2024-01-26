package com.ssafy.bid.domain.board;

import lombok.Getter;

@Getter
public enum BiddingStatus {

	BIDDING("입찰"),
	WINNING_BID("낙찰"),
	FAILING_BID("유찰");

	private final String biddingStatus;

	private BiddingStatus(String biddingStatus) {
		this.biddingStatus = biddingStatus;
	}
}

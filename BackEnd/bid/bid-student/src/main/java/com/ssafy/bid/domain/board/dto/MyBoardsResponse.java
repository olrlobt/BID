package com.ssafy.bid.domain.board.dto;

import java.util.List;

import lombok.Data;

@Data
public class MyBoardsResponse {

	private List<BoardResponse> myBoards;
	private List<BoardResponse> myBiddingBoards;

	public MyBoardsResponse(List<BoardResponse> myBoards, List<BoardResponse> myBiddingBoards) {
		this.myBoards = myBoards;
		this.myBiddingBoards = myBiddingBoards;
	}
}

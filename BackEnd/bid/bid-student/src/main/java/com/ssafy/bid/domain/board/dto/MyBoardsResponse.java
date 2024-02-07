package com.ssafy.bid.domain.board.dto;

import java.util.List;

import lombok.Data;

@Data
public class MyBoardsResponse {

	private List<BoardListResponse> myBoards;
	private List<BoardListResponse> myBiddingBoards;

	public MyBoardsResponse(List<BoardListResponse> myBoards, List<BoardListResponse> myBiddingBoards) {
		this.myBoards = myBoards;
		this.myBiddingBoards = myBiddingBoards;
	}
}

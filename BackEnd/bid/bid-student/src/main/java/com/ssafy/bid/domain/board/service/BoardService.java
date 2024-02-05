package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	public List<BoardResponse> findBoards(int gradeNo, String keyword) {
		return boardRepository.findBoards(gradeNo, keyword);
	}

	public MyBoardsResponse findMyAllBoards(int userNo) {

		List<BoardResponse> myBoards = boardRepository.findMyBoards(userNo);
		List<BoardResponse> myBiddingBoards = boardRepository.findMyBiddingBoards(userNo);

		return new MyBoardsResponse(myBoards, myBiddingBoards);
	}
}

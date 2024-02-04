package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	public List<BoardResponse> findBoards(int gradeNo, String category, String keyword) {
		Category from = Category.from(category);
		return boardRepository.findBoards(gradeNo, from, keyword);
	}

	public List<BoardResponse> findMyBoards(int userNo) {
		return boardRepository.findMyBoards(userNo);
	}
}

package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	public List<BoardResponse> findAllStudentBoards(int gradeNo){
		return boardRepository.findAllStudentBoards(gradeNo);
	}
}
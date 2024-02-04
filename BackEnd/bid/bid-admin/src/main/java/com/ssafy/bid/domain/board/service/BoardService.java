package com.ssafy.bid.domain.board.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

	public List<BoardResponse> findAllStudentBoards(int gradeNo) {
		return boardRepository.findAllStudentBoards(gradeNo);
	}

	public BoardResponse getStudentBoard(int gradeNo, long boardNo) {
		Optional<BoardResponse> studentBoard = boardRepository.getStudentBoard(gradeNo, boardNo);

		if (studentBoard.isEmpty()) {
			throw new NoSuchElementException("해당 게시글이 없습니다.");
		}

		return studentBoard.get();
	}
}

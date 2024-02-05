package com.ssafy.bid.domain.board.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoreBoardService {

	private final CoreBoardRepository coreBoardRepository;

	public BoardResponse getBoardDetail(long boardNo) {
		return coreBoardRepository.getStudentBoard(boardNo)
			.orElseThrow(() -> new NoSuchElementException("해당 글이 없습니다."));
	}
}

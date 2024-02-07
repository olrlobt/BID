package com.ssafy.bid.domain.board.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;
import com.ssafy.bid.domain.board.repository.CoreReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoreBoardService {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreReplyRepository coreReplyRepository;

	public BoardResponse getBoardDetail(long boardNo) {
		BoardResponse boardResponse = coreBoardRepository.getStudentBoard(boardNo)
			.orElseThrow(() -> new NoSuchElementException("해당 글이 없습니다."));

		boardResponse.setReplies(coreReplyRepository.findReplies(boardResponse.getNo()));
		return boardResponse;
	}
}

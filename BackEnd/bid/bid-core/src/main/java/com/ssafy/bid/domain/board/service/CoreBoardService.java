package com.ssafy.bid.domain.board.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.Bidding;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.CoreBiddingRepository;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;
import com.ssafy.bid.domain.board.repository.CoreReplyRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoreBoardService {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreReplyRepository coreReplyRepository;
	private final CoreBiddingRepository coreBiddingRepository;

	public BoardResponse getBoardDetail(int userNo, long boardNo, int gradeNo) {

		// user의 gradeNo이 넘겨받은 gradeNo이 아닐경우 예외 처리

		BoardResponse boardResponse = coreBoardRepository.getStudentBoard(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 글이 없습니다.", boardNo));

		coreBiddingRepository.findByUserNoAndBoardNo(userNo, boardNo).ifPresent(value -> boardResponse.setDisplayPrice(value.getPrice()));

		boardResponse.setComments(coreReplyRepository.findReplies(boardResponse.getNo()));
		return boardResponse;
	}
}

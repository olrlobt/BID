package com.ssafy.bid.domain.board.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.CoreBiddingRepository;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;
import com.ssafy.bid.domain.board.repository.CoreReplyRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreBoardService {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreReplyRepository coreReplyRepository;
	private final CoreBiddingRepository coreBiddingRepository;
	private final TaskScheduler taskScheduler;

	public BoardResponse getBoardDetail(int userNo, long boardNo, int gradeNo) {

		// user의 gradeNo이 넘겨받은 gradeNo이 아닐경우 예외 처리

		BoardResponse boardResponse = coreBoardRepository.getStudentBoard(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 글이 없습니다.", boardNo));

		coreBiddingRepository.findByUserNoAndBoardNo(userNo, boardNo)
			.ifPresent(value -> boardResponse.setDisplayPrice(value.getPrice()));

		boardResponse.setComments(coreReplyRepository.findReplies(boardResponse.getNo()));
		return boardResponse;
	}

	@Transactional
	public void registerBoardTask(LocalTime time, long boardNo) {

		LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), time);
		Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();

		taskScheduler.schedule(() -> {

			Board board = coreBoardRepository.findById(boardNo)
				.orElseThrow(() -> new ResourceNotFoundException("게시물이 없습니다.", boardNo));

			board.complete();

			coreBiddingRepository.findAllByBoardNo(board.getNo())
				.forEach(bidding -> {
					if (bidding.getNo().equals(board.getBiddingNo())) {
						bidding.bidFail();
						// 유찰 알림
					} else {
						bidding.bidSuccess();
						// 낙찰 알림
					}
				});

			// 낙찰자가 없을 경우?
		}, instant);
	}

}

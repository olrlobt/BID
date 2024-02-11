package com.ssafy.bid.domain.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.repository.CoreBiddingRepository;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoreBoardScheduleService {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreBiddingRepository coreBiddingRepository;

	@Transactional
	protected void bidProgress(long boardNo) {
		Board board = coreBoardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("게시물이 없습니다.", boardNo));

		board.complete();

		// 낙찰자가 없을 경우 선행 처리


		// 쿠폰

		// 대포

		// 경매 게시글
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

	}

}

package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Bidding;
import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.CoreBiddingRepository;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;
import com.ssafy.bid.domain.board.repository.CoreReplyRepository;
import com.ssafy.bid.domain.coupon.UserCoupon;
import com.ssafy.bid.domain.coupon.repository.CoreUserCouponRepository;
import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.repository.CoreStudentRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreBoardService {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreReplyRepository coreReplyRepository;
	private final CoreBiddingRepository coreBiddingRepository;
	private final NotificationService notificationService;
	private final CoreStudentRepository coreStudentRepository;
	private final CoreUserCouponRepository coreUserCouponRepository;

	@Transactional(readOnly = true)
	public BoardResponse getBoardDetail(int userNo, long boardNo, int gradeNo) {

		// user의 gradeNo이 넘겨받은 gradeNo이 아닐경우 예외 처리

		BoardResponse boardResponse = coreBoardRepository.getStudentBoard(boardNo, gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 글이 없습니다.", boardNo));

		coreBiddingRepository.findByUserNoAndBoardNo(userNo, boardNo)
			.ifPresent(value -> boardResponse.setDisplayPrice(value.getPrice()));

		boardResponse.setComments(coreReplyRepository.findReplies(boardResponse.getNo()));
		return boardResponse;
	}

	@Transactional
	public Board addBoard(int userNo, int gradeNo, BoardCreateRequest boardCreateRequest) {
		Board board = boardCreateRequest.toEntity(userNo, gradeNo);
		return coreBoardRepository.save(board);
	}

	@Transactional
	protected void bidProgress(long boardNo) {
		Board board = coreBoardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("게시물이 없습니다.", boardNo));

		board.complete();

		// 낙찰자가 없을 경우 선행 처리
		List<Bidding> allBidding = coreBiddingRepository.findAllByBoardNo(board.getNo());
		if (allBidding.isEmpty()) {
			return;
		}

		allBidding.forEach(bidding -> {

			Student student = coreStudentRepository.findById(bidding.getUserNo())
				.orElseThrow(() -> new ResourceNotFoundException("해당 회원이 없습니다.", bidding.getUserNo()));

			String bidStatus;

			if (bidding.getNo().equals(board.getBiddingNo())) {
				bidStatus = "낙찰";
				bidding.bidSuccess();

				if (board.getCategory() == Category.COUPON) {

					coreUserCouponRepository.save(UserCoupon.builder()
						.couponNo(board.getSubNo())
						.gradeNo(board.getGradeNo())
						.userNo(board.getUserNo())
						.build());

				} else if (board.getCategory() == Category.CANNON) {
					student.addBall();
				}

			} else {
				bidStatus = "유찰";
				bidding.bidFail();
				student.addPrice(bidding.getPrice());
			}

			notificationService.send(NotificationRequest.builder()
				.title(board.getTitle() + " " + bidStatus)
				.content(board.getTitle() + " 경매에 " + bidStatus + " 되었어요.")
				.receiverNo(bidding.getUserNo())
				.notificationType(NotificationType.BIDDING_END)
				.build());
		});

	}
}

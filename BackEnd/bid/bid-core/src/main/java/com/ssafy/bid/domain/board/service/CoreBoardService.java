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

		List<Bidding> biddings = coreBiddingRepository.findAllByBoardNoLimit(board.getNo());
		StringBuilder forWinner = new StringBuilder();
		forWinner.append(board.getTitle()).append(" 경매에 낙찰되었어요");
		for (int i = 1; i < biddings.size(); i++) {
			forWinner.append(i+1).append("등 입찰가 : ").append(biddings.get(i).getPrice()).append("\n");
		}
		StringBuilder forLoser = new StringBuilder();
		forLoser.append(board.getTitle()).append(" 경매에 유찰되었어요");
		for (int i = 0; i < biddings.size(); i++) {
			if (i >= 5) {
				break;
			}
			forLoser.append(i+1).append("등 입찰가 : ").append(biddings.get(i).getPrice()).append("\n");
		}

		allBidding.forEach(bidding -> {

			Student student = coreStudentRepository.findById(bidding.getUserNo())
				.orElseThrow(() -> new ResourceNotFoundException("해당 회원이 없습니다.", bidding.getUserNo()));

			if (bidding.getNo().equals(board.getBiddingNo())) {

				bidding.bidSuccess();

				if (board.getCategory() == Category.COUPON) {
					notificationService.send(NotificationRequest.builder()
						.title(board.getTitle())
						.content(forWinner.toString())
						.receiverNo(bidding.getUserNo())
						.notificationType(NotificationType.BIDDING_WINNING)
						.subNo(board.getNo())
						.build());
					coreUserCouponRepository.save(UserCoupon.builder()
						.couponNo(board.getSubNo())
						.gradeNo(board.getGradeNo())
						.userNo(board.getUserNo())
						.build());

				} else if (board.getCategory() == Category.CANNON) {
					student.addBall();
				}

			} else {
				notificationService.send(NotificationRequest.builder()
					.title(board.getTitle())
					.content(forLoser.toString())
					.receiverNo(bidding.getUserNo())
					.notificationType(NotificationType.BIDDING_FAILED)
					.build());

				bidding.bidFail();
				student.addPrice(bidding.getPrice());
			}
		});

	}
}

package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;
import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradeProjection;
import com.ssafy.bid.domain.grade.repository.GradeRepository;
import com.ssafy.bid.domain.grade.repository.StudentRepository;
import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	private final GradeRepository gradeRepository;
	private final NotificationService notificationService;
	private final StudentRepository studentRepository;
	private final CoreBoardScheduleService coreBoardScheduleService;

	public List<BoardListResponse> findAllStudentBoards(int gradeNo, int userNo) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (grade.getUserNo() != userNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}
		return boardRepository.findAllStudentBoards(gradeNo, gradeNo);
	}

	@Transactional
	public void deleteBoard(long boardNo, int gradeNo, int userNo, UserType userType) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (grade.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		} else if (!boardRepository.existsById(boardNo)) {
			throw new ResourceNotFoundException("해당 게시글이 없습니다.", boardNo);
		}
		coreBoardScheduleService.cancelScheduledTask(boardNo);
		boardRepository.deleteById(boardNo);
	}

	@Transactional
	public void deleteReply(long replyNo, int gradeNo, int userNo, UserType userType) {

		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		} else if (!replyRepository.existsById(replyNo)) {
			throw new ResourceNotFoundException("해당 댓글이 없습니다.", replyNo);
		}
		replyRepository.deleteById(replyNo);
	}

	@Transactional
	public boolean holdBoards(int gradeNo, int userNo, UserType userType) {

		GradeProjection gradeProjection = gradeRepository.findByNo(gradeNo)
			.orElseThrow(() -> new AuthorizationFailedException("권한이 없습니다."));

		if (gradeProjection.getUserNo() != userNo || !userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학급이 없습니다.", gradeNo));

		studentRepository.findAllByGradeNo(gradeNo).forEach(student -> {
			NotificationRequest notificationRequest = makeNotificationRequest(student.getNo(), grade.isHold());
			notificationService.send(notificationRequest);
		});

		return grade.holdBidToggle();
	}

	private NotificationRequest makeNotificationRequest(int userNo, boolean isHold) {
		String content = !isHold ? "시작" : "정지";

		return NotificationRequest.builder()
			.receiverNo(userNo)
			.title("경매 " + content)
			.content("경매가 " + content + " 되었어요")
			.notificationType(NotificationType.ETC)
			.build();
	}

	@Transactional
	public void holdBid(int gradeNo) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학급이 없습니다.", gradeNo));

		grade.holdBid();
	}

	@Transactional
	public void unHoldBid(int gradeNo) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학급이 없습니다.", gradeNo));

		grade.unHoldBid();
	}
}

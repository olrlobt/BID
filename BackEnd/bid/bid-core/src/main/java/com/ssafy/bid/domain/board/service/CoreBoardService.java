package com.ssafy.bid.domain.board.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.CoreBiddingRepository;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;
import com.ssafy.bid.domain.board.repository.CoreReplyRepository;
import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;
import com.ssafy.bid.domain.coupon.repository.CoreCouponRepository;
import com.ssafy.bid.domain.grade.dto.GradeProjection;
import com.ssafy.bid.domain.grade.repository.CoreGradeRepository;
import com.ssafy.bid.domain.gradeperiod.GradePeriod;
import com.ssafy.bid.domain.gradeperiod.repository.CoreGradePeriodRepository;
import com.ssafy.bid.domain.gradeperiod.service.CoreGradePeriodService;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreBoardService {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreReplyRepository coreReplyRepository;
	private final CoreBiddingRepository coreBiddingRepository;
	private final TaskScheduler taskScheduler;
	private final CoreBoardScheduleService coreBoardScheduleService;

	private final CoreGradeRepository coreGradeRepository;
	private final CoreCouponRepository coreCouponRepository;
	private final CoreGradePeriodRepository coreGradePeriodRepository;
	private final CoreGradePeriodService coreGradePeriodService;

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
			coreBoardScheduleService.job(boardNo);
		}, instant);
	}

	@Scheduled(cron = "0 0 9 * * *")
	@Transactional
	public void addWeeklyCoupon(){
		// 모든 학급의 쿠폰
		List<GradeProjection> allGradeNo = coreGradeRepository.findBy();

		for (GradeProjection gradeProjection : allGradeNo) {
			int gradeNo = gradeProjection.getNo();
			int userNo = gradeProjection.getUserNo();

			// 해당 학급의 모든 쿠폰
			List<Coupon> allCoupons = coreCouponRepository.findAllByGradeNoAndCouponStatus(gradeNo,
				CouponStatus.REGISTERED);

			if (!allCoupons.isEmpty()) {
				Coupon coupon = allCoupons.get(ThreadLocalRandom.current().nextInt(allCoupons.size()));

				BoardCreateRequest boardCreateRequest = BoardCreateRequest.builder()
					.title(coupon.getName())
					.description(coupon.getDescription())
					.startPrice(coupon.getStartPrice())
					.category(Category.COUPON)
					.goodsImgUrl("urltest")
					.gradePeriodNo(6)
					.build();

				Board board = boardCreateRequest.toEntity(userNo, gradeNo);
				Board save = coreBoardRepository.save(board);

				LocalTime startTime = coreGradePeriodService.findStartTime(gradeNo, 6);
				registerBoardTask(startTime, save.getNo());
			}
		}
	}
}

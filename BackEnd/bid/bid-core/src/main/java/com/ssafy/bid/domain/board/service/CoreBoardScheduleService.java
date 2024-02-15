package com.ssafy.bid.domain.board.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;
import com.ssafy.bid.domain.coupon.repository.CoreCouponRepository;
import com.ssafy.bid.domain.grade.dto.GradeProjection;
import com.ssafy.bid.domain.grade.repository.CoreGradeRepository;
import com.ssafy.bid.domain.gradeperiod.service.CoreGradePeriodService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoreBoardScheduleService {

	private final TaskScheduler taskScheduler;

	private final CoreGradeRepository coreGradeRepository;
	private final CoreCouponRepository coreCouponRepository;
	private final CoreGradePeriodService coreGradePeriodService;

	private final CoreBoardService coreBoardService;
	private final Map<Long, ScheduledFuture<?>> boardScheduledTasks = new ConcurrentHashMap<>();

	@Scheduled(cron = "0 0 9 * * *")
	public void addWeeklyCoupon() {
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
					.subNo(coupon.getNo())
					.build();

				Board board = coreBoardService.addBoard(userNo, gradeNo, boardCreateRequest);
				registerBoardTask(board);

			}
			// 대포 등록
			BoardCreateRequest cannonDto = BoardCreateRequest.createCannon();
			Board board = coreBoardService.addBoard(userNo, gradeNo, cannonDto);
			registerBoardTask(board);
		}
	}

	public void registerBoardTask(Board board) {
		LocalTime startTime = coreGradePeriodService.findStartTime(board.getGradeNo(), board.getGradePeriodNo());
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), startTime);
		Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();

		boardScheduledTasks.put(board.getNo(), taskScheduler.schedule(() -> {
			coreBoardService.bidProgress(board.getNo());
		}, instant));
	}

	public void cancelScheduledTask(long boardNo) {
		ScheduledFuture<?> scheduledFuture = boardScheduledTasks.get(boardNo);
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}
	}
}

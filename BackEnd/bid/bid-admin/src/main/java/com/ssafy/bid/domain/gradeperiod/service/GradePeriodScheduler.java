package com.ssafy.bid.domain.gradeperiod.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.board.service.BoardService;
import com.ssafy.bid.domain.gradeperiod.GradePeriod;
import com.ssafy.bid.global.util.CronExpression;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradePeriodScheduler {

	private final TaskScheduler taskScheduler;
	private final Map<Integer, Map<Integer, ScheduledFuture<?>>> gradeScheduledTasks = new ConcurrentHashMap<>();
	private final BoardService boardService;

	public void scheduleClassLessonTask(GradePeriod gradePeriod) {
		String cronExpressionOfStart = CronExpression.of(gradePeriod.getStartPeriod());
		String cronExpressionOfEnd = CronExpression.of(gradePeriod.getEndPeriod());

		Map<Integer, ScheduledFuture<?>> gradePeriodScheduled = gradeScheduledTasks.getOrDefault(
			gradePeriod.getGradeNo(),
			new ConcurrentHashMap<>());

		cancelScheduledTask(gradePeriod.getGradeNo(), gradePeriod.getNo());
		cancelScheduledTask(gradePeriod.getGradeNo(), gradePeriod.getNo());

		gradePeriodScheduled.put(gradePeriod.getNo(), taskScheduler.schedule(() -> {
			boardService.holdBid(gradePeriod.getGradeNo());
		}, new CronTrigger(cronExpressionOfStart)));

		gradePeriodScheduled.put(gradePeriod.getNo(), taskScheduler.schedule(() -> {
			boardService.unHoldBid(gradePeriod.getGradeNo());
		}, new CronTrigger(cronExpressionOfEnd)));
		gradeScheduledTasks.put(gradePeriod.getGradeNo(), gradePeriodScheduled);
	}

	public void cancelScheduledTask(int gradeNo, int gradePeriodNo) {
		Map<Integer, ScheduledFuture<?>> sequenceScheduledTasks = gradeScheduledTasks.get(gradeNo);
		if (sequenceScheduledTasks != null) {
			ScheduledFuture<?> scheduledFuture = sequenceScheduledTasks.get(gradePeriodNo);
			if (scheduledFuture != null) {
				scheduledFuture.cancel(false);
			}
		}
	}

}

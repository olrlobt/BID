package com.ssafy.bid.domain.gradeperiod.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ssafy.bid.domain.gradeperiod.repository.GradePeriodRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GradePeriodSchedulerInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final GradePeriodScheduler gradePeriodScheduler;
	private final GradePeriodRepository gradePeriodRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		gradePeriodRepository.findAll().forEach(gradePeriodScheduler::scheduleClassLessonTask);
	}
}

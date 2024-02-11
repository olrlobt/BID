package com.ssafy.bid.domain.gradeperiod.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.gradeperiod.GradePeriod;
import com.ssafy.bid.domain.gradeperiod.repository.CoreGradePeriodRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreGradePeriodService {

	private final CoreGradePeriodRepository coreGradePeriodRepository;

	public LocalTime findStartTime(int gradeNo, int sequence) {

		GradePeriod gradePeriod = coreGradePeriodRepository.findByGradeNoAndSequence(gradeNo,
			sequence).orElseThrow(() -> new ResourceNotFoundException("수업시간이 등록되어있지 않습니다.", gradeNo, sequence));
		return gradePeriod.getStartPeriod();
	}
}

package com.ssafy.bid.domain.gradeperiod.service;

import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.gradeperiod.repository.CoreGradePeriodRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoreGradePeriodService {

	private CoreGradePeriodRepository coreGradePeriodRepository;

	public LocalTime findStartTime(int gradeNo, int sequence) {

		return coreGradePeriodRepository.findByGradeNoAndSequence(gradeNo, sequence).getStartPeriod();
	}
}

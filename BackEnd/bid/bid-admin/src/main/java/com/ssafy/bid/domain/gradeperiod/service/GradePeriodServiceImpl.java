package com.ssafy.bid.domain.gradeperiod.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodModifyRequest;
import com.ssafy.bid.domain.gradeperiod.repository.GradePeriodRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GradePeriodServiceImpl implements GradePeriodService {

	private final GradePeriodRepository gradePeriodRepository;

	@Override
	public void modifyGradePeriod(int gradeNo, GradePeriodModifyRequest gradePeriodModifyRequest) {
		gradePeriodRepository.findAllByGradeNo(gradeNo).forEach(gradePeriod ->
			gradePeriodModifyRequest.getGradePeriodsRequests().stream()
				.filter(request -> gradePeriod.getNo().equals(request.getNo()))
				.forEach(request -> gradePeriod.modify(request.getStartPeriod(), request.getEndPeriod()))
		);
	}
}

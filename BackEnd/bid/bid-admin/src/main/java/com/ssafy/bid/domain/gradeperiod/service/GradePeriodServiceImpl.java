package com.ssafy.bid.domain.gradeperiod.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodListUpdateRequest;
import com.ssafy.bid.domain.gradeperiod.repository.GradePeriodRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GradePeriodServiceImpl implements GradePeriodService {

	private final GradePeriodRepository gradePeriodRepository;

	@Override
	public void updateGradePeriod(int gradeNo, GradePeriodListUpdateRequest gradePeriodListUpdateRequest) {
		gradePeriodRepository.findAllByGradeNo(gradeNo).forEach(gradePeriod ->
			gradePeriodListUpdateRequest.getGradePeriodUpdateRequests().stream()
				.filter(request -> gradePeriod.getNo().equals(request.getNo()))
				.forEach(request -> gradePeriod.update(request.getStartPeriod(), request.getEndPeriod()))
		);
	}
}

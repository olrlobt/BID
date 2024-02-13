package com.ssafy.bid.domain.gradeperiod.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.gradeperiod.dto.GradePeriodListUpdateRequest;
import com.ssafy.bid.domain.gradeperiod.repository.GradePeriodRepository;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GradePeriodServiceImpl implements GradePeriodService {

	private final GradePeriodRepository gradePeriodRepository;

	@Override
	public void updateGradePeriod(UserType userType, int gradeNo,
		GradePeriodListUpdateRequest gradePeriodListUpdateRequest) {
		if (!userType.equals(UserType.ADMIN)) {
			throw new AuthorizationFailedException("교시수정: Admin 권한 사용자가 아님.");
		}

		gradePeriodRepository.findAllByGradeNo(gradeNo).forEach(gradePeriod ->
			gradePeriodListUpdateRequest.getGradePeriodUpdateRequests().stream()
				.filter(request -> gradePeriod.getNo().equals(request.getNo()))
				.forEach(request -> gradePeriod.update(request.getStartPeriod(), request.getEndPeriod()))
		);
	}
}

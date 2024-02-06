package com.ssafy.bid.domain.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;
import com.ssafy.bid.domain.grade.repository.CoreGradePeriodRepository;
import com.ssafy.bid.domain.grade.repository.CoreGradeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreGradeServiceImpl implements CoreGradeService {

	private final CoreGradeRepository coreGradeRepository;
	private final CoreGradePeriodRepository coreGradePeriodRepository;

	@Override
	public GradeStatisticsFindResponse findGradeStatistics(int gradeNo) {
		List<GradePeriodsFindResponse> periods = coreGradePeriodRepository.findAllGradePeriodByGradeNo(gradeNo);
		GradeStatisticsFindResponse statistics = coreGradeRepository.findGradeStatisticsByGradeNo(gradeNo)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 학급이 없음"));//TODO: 커스텀 예외처리
		statistics.setGradePeriodsFindResponses(periods);
		return statistics;
	}
}

package com.ssafy.bid.domain.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradePeriodsGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsGetResponse;
import com.ssafy.bid.domain.grade.repository.CoreGradePeriodRepository;
import com.ssafy.bid.domain.grade.repository.CoreGradeRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreGradeServiceImpl implements CoreGradeService {

	private final CoreGradeRepository coreGradeRepository;
	private final CoreGradePeriodRepository coreGradePeriodRepository;

	@Override
	public GradeStatisticsGetResponse findGradeStatistics(int gradeNo) {
		List<GradePeriodsGetResponse> periods = coreGradePeriodRepository.findAllGradePeriodByGradeNo(gradeNo);

		GradeStatisticsGetResponse statistics = coreGradeRepository.findGradeStatisticsByGradeNo(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("조회하려는 Grade 엔티티가 없음.", gradeNo));

		statistics.setGradePeriodsGetResponses(periods);
		return statistics;
	}

	@Override
	@Transactional
	public void updateGradeStatistics() {
		List<Grade> grades = coreGradeRepository.findAll();

		coreGradeRepository.findAllBiddingDealTypeStatistics().forEach(response -> grades.stream()
			.filter(grade -> grade.getNo() == response.getGradeNo())
			.forEach(grade -> grade.getExpenditureStatistics().updateExpenditureStatistics(response)));

		coreGradeRepository.findAllWinningBiddingStatistics().forEach(response -> grades.stream()
			.filter(grade -> grade.getNo() == response.getGradeNo())
			.forEach(grade -> grade.getBiddingStatistics().updateBiddingStatistics(response)));
	}
}

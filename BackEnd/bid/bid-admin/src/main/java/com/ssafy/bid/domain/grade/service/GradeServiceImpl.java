package com.ssafy.bid.domain.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.BiddingsFindResponse;
import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;

	@Override
	@Transactional(readOnly = true)
	public GradeFindResponse findGrade(int gradeNo) {
		List<BiddingsFindResponse> biddings = gradeRepository.findBiddings(gradeNo);
		List<GradePeriodsFindResponse> gradePeriods = gradeRepository.findGradePeriods(gradeNo);
		GradeFindResponse gradeFindResponse = gradeRepository.findGrade(gradeNo)
			.orElseThrow(() -> new IllegalArgumentException(""));//TODO: 커스텀 예외처리

		gradeFindResponse.setWinningBiddingCounts(biddings);
		gradeFindResponse.setGradePeriods(gradePeriods);

		return gradeFindResponse;
	}

	@Override
	public void modifySalary(int gradeNo, SalaryModifyRequest salaryModifyRequest) {
		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new IllegalArgumentException(""));//TODO: 커스텀 예외처리

		grade.modifySalary(salaryModifyRequest.getSalary());
	}
}

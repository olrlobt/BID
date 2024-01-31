package com.ssafy.bid.domain.grade.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.grade.dto.BiddingsFindResponse;
import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;
import com.ssafy.bid.domain.grade.repository.GradeRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GradeServiceImpl implements GradeService {

	private final GradeRepository gradeRepository;

	@Override
	public GradeFindResponse findGrade(int gradeNo) {
		List<BiddingsFindResponse> biddings = gradeRepository.findBiddings(gradeNo);
		List<GradePeriodsFindResponse> gradePeriods = gradeRepository.findGradePeriods(gradeNo);
		GradeFindResponse gradeFindResponse = gradeRepository.findGrade(gradeNo)
			.orElseThrow(() -> new IllegalArgumentException(""));//TODO: 커스텀 예외처리

		gradeFindResponse.setWinningBiddingCounts(biddings);
		gradeFindResponse.setGradePeriods(gradePeriods);

		return gradeFindResponse;
	}
}

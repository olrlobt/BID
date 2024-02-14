package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.bid.domain.gradeperiod.GradePeriod;

import lombok.Getter;

@Getter
public class GradePeriodRequest {
	private int gradeNo;

	public GradePeriodRequest(int gradeNo) {
		this.gradeNo = gradeNo;
	}

	public List<GradePeriod> toEntity() {
		List<GradePeriod> gradePeriods = new ArrayList<>();
		gradePeriods.add(createFirst());
		gradePeriods.add(createSecond());
		gradePeriods.add(createThird());
		gradePeriods.add(createForth());
		gradePeriods.add(createFifth());
		gradePeriods.add(createSixth());
		return gradePeriods;
	}

	private GradePeriod createFirst() {
		return GradePeriod.builder()
			.sequence(1)
			.startPeriod(LocalTime.of(9, 0, 0))
			.endPeriod(LocalTime.of(9, 40, 0))
			.gradeNo(gradeNo)
			.build();
	}

	private GradePeriod createSecond() {
		return GradePeriod.builder()
			.sequence(2)
			.startPeriod(LocalTime.of(10, 0, 0))
			.endPeriod(LocalTime.of(10, 40, 0))
			.gradeNo(gradeNo)
			.build();
	}

	private GradePeriod createThird() {
		return GradePeriod.builder()
			.sequence(3)
			.startPeriod(LocalTime.of(11, 0, 0))
			.endPeriod(LocalTime.of(11, 40, 0))
			.gradeNo(gradeNo)
			.build();
	}

	private GradePeriod createForth() {
		return GradePeriod.builder()
			.sequence(4)
			.startPeriod(LocalTime.of(13, 0, 0))
			.endPeriod(LocalTime.of(13, 40, 0))
			.gradeNo(gradeNo)
			.build();
	}

	private GradePeriod createFifth() {
		return GradePeriod.builder()
			.sequence(5)
			.startPeriod(LocalTime.of(14, 0, 0))
			.endPeriod(LocalTime.of(14, 40, 0))
			.gradeNo(gradeNo)
			.build();
	}

	private GradePeriod createSixth() {
		return GradePeriod.builder()
			.sequence(6)
			.startPeriod(LocalTime.of(15, 0, 0))
			.endPeriod(LocalTime.of(15, 40, 0))
			.gradeNo(gradeNo)
			.build();
	}
}

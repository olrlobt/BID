package com.ssafy.bid.domain.grade.dto;

import com.ssafy.bid.domain.user.DealType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExpenditureStatisticsFindResponse {
	private int sumExpenditure;
	private DealType dealType;
	private int gradeNo;

	public ExpenditureStatisticsFindResponse(
		int sumExpenditure,
		DealType dealType,
		int gradeNo
	) {
		this.sumExpenditure = sumExpenditure;
		this.dealType = dealType;
		this.gradeNo = gradeNo;
	}
}

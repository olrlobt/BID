package com.ssafy.bid.domain.grade;

import com.ssafy.bid.domain.grade.dto.ExpenditureStatisticsGetResponse;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class ExpenditureStatistics {
	private int sumSnackExpenditure;
	private int sumLearningExpenditure;
	private int sumCouponExpenditure;
	private int sumGameExpenditure;
	private int sumEtcExpenditure;

	public void updateExpenditureStatistics(ExpenditureStatisticsGetResponse response) {
		switch (response.getDealType()) {
			case SNACK -> this.sumSnackExpenditure = response.getSumExpenditure();
			case LEARNING -> this.sumLearningExpenditure = response.getSumExpenditure();
			case COUPON -> this.sumCouponExpenditure = response.getSumExpenditure();
			case GAME -> this.sumGameExpenditure = response.getSumExpenditure();
			default -> this.sumEtcExpenditure = response.getSumExpenditure();
		}
	}
}

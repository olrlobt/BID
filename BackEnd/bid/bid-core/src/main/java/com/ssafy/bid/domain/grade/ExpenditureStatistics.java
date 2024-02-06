package com.ssafy.bid.domain.grade;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ExpenditureStatistics {
	private int sumSnackExpenditure;
	private int sumLearningExpenditure;
	private int sumCouponExpenditure;
	private int sumGameExpenditure;
	private int sumEtcExpenditure;
}

package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeStatisticsFindResponse {
	private int unapprovedCouponCount;
	private int salary;
	private int sumTotalExpenditure;
	private int sumSnackExpenditure;
	private int sumLearningExpenditure;
	private int sumCouponExpenditure;
	private int sumGameExpenditure;
	private int sumEtcExpenditure;
	private int asset;
	private String transferAlertPeriod;
	private String transferPeriod;
	private int countFourteenDaysAgo;
	private int countThirteenDaysAgo;
	private int countTwelveDaysAgo;
	private int countElevenDaysAgo;
	private int countTenDaysAgo;
	private int countNineDaysAgo;
	private int countEightDaysAgo;
	private int countSevenDaysAgo;
	private int countSixDaysAgo;
	private int countFiveDaysAgo;
	private int countFourDaysAgo;
	private int countThreeDaysAgo;
	private int countTwoDaysAgo;
	private int countOneDaysAgo;
	private List<GradePeriodsFindResponse> gradePeriodsFindResponses;

	public GradeStatisticsFindResponse(
		long unapprovedCouponCount,
		int salary,
		int sumSnackExpenditure,
		int sumLearningExpenditure,
		int sumCouponExpenditure,
		int sumGameExpenditure,
		int sumEtcExpenditure,
		int asset,
		LocalTime transferAlertPeriod,
		LocalTime transferPeriod,
		int countFourteenDaysAgo,
		int countThirteenDaysAgo,
		int countTwelveDaysAgo,
		int countElevenDaysAgo,
		int countTenDaysAgo,
		int countNineDaysAgo,
		int countEightDaysAgo,
		int countSevenDaysAgo,
		int countSixDaysAgo,
		int countFiveDaysAgo,
		int countFourDaysAgo,
		int countThreeDaysAgo,
		int countTwoDaysAgo,
		int countOneDaysAgo
	) {
		this.unapprovedCouponCount = (int)unapprovedCouponCount;
		this.salary = salary;
		this.sumSnackExpenditure = sumSnackExpenditure;
		this.sumLearningExpenditure = sumLearningExpenditure;
		this.sumCouponExpenditure = sumCouponExpenditure;
		this.sumGameExpenditure = sumGameExpenditure;
		this.sumEtcExpenditure = sumEtcExpenditure;
		this.sumTotalExpenditure =
			sumSnackExpenditure + sumLearningExpenditure + sumCouponExpenditure + sumGameExpenditure
				+ sumEtcExpenditure;
		this.asset = asset;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		this.transferAlertPeriod = transferAlertPeriod.format(formatter);
		this.transferPeriod = transferPeriod.format(formatter);
		this.countFourteenDaysAgo = countFourteenDaysAgo;
		this.countThirteenDaysAgo = countThirteenDaysAgo;
		this.countTwelveDaysAgo = countTwelveDaysAgo;
		this.countElevenDaysAgo = countElevenDaysAgo;
		this.countTenDaysAgo = countTenDaysAgo;
		this.countNineDaysAgo = countNineDaysAgo;
		this.countEightDaysAgo = countEightDaysAgo;
		this.countSevenDaysAgo = countSevenDaysAgo;
		this.countSixDaysAgo = countSixDaysAgo;
		this.countFiveDaysAgo = countFiveDaysAgo;
		this.countFourDaysAgo = countFourDaysAgo;
		this.countThreeDaysAgo = countThreeDaysAgo;
		this.countTwoDaysAgo = countTwoDaysAgo;
		this.countOneDaysAgo = countOneDaysAgo;
	}

	public void setGradePeriodsFindResponses(List<GradePeriodsFindResponse> gradePeriodsFindResponses) {
		this.gradePeriodsFindResponses = gradePeriodsFindResponses;
	}
}

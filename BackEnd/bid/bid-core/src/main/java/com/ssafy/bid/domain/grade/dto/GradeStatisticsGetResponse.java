package com.ssafy.bid.domain.grade.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GradeStatisticsGetResponse {
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
	private int salaryRecommendation;
	private boolean isDangerInInflation;
	private boolean isDangerInDeflation;
	private List<BiddingStatisticsFindResponses> biddingStatisticsFindResponses;
	private List<GradePeriodsGetResponse> gradePeriodsGetResponses;

	public GradeStatisticsGetResponse(
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
		int countOneDaysAgo,
		int salaryRecommendation,
		boolean isDangerInInflation,
		boolean isDangerInDeflation
	) {
		this.unapprovedCouponCount = (int)unapprovedCouponCount;
		this.salary = salary;
		this.sumSnackExpenditure = sumSnackExpenditure;
		this.sumLearningExpenditure = sumLearningExpenditure;
		this.sumCouponExpenditure = sumCouponExpenditure;
		this.sumGameExpenditure = sumGameExpenditure;
		this.sumEtcExpenditure = sumEtcExpenditure;
		this.sumTotalExpenditure = sumSnackExpenditure + sumLearningExpenditure + sumCouponExpenditure
			+ sumGameExpenditure + sumEtcExpenditure;
		this.asset = asset;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		this.transferAlertPeriod = transferAlertPeriod.format(formatter);
		this.transferPeriod = transferPeriod.format(formatter);
		List<BiddingStatisticsFindResponses> template = new ArrayList<>();
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(14))
			.count(countFourteenDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(13))
			.count(countThirteenDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(12))
			.count(countTwelveDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(11))
			.count(countElevenDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(10))
			.count(countTenDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(9))
			.count(countNineDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(8))
			.count(countEightDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(7))
			.count(countSevenDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(6))
			.count(countSixDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(5))
			.count(countFiveDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(4))
			.count(countFourDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(3))
			.count(countThreeDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(2))
			.count(countTwoDaysAgo).build());
		template.add(BiddingStatisticsFindResponses.builder().date(LocalDate.now().minusDays(1))
			.count(countOneDaysAgo).build());
		this.biddingStatisticsFindResponses = template;
		this.salaryRecommendation = salaryRecommendation;
		this.isDangerInInflation = isDangerInInflation();
		this.isDangerInDeflation = isDangerInDeflation;
	}

	public void setGradePeriodsGetResponses(List<GradePeriodsGetResponse> gradePeriodsGetRespons) {
		this.gradePeriodsGetResponses = gradePeriodsGetRespons;
	}
}

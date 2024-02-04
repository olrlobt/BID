package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GradeFindResponse {
	private long unapprovedCouponCount;
	private int salary;
	private int totalCategorySum;
	private int snackSum;
	private int learningSum;
	private int couponSum;
	private int gameSum;
	private int etcSum;
	private int asset;
	private LocalTime transferAlertPeriod;
	private LocalTime transferPeriod;
	private List<BiddingsFindResponse> winningBiddingCounts;
	private List<GradePeriodsFindResponse> gradePeriods;

	public GradeFindResponse(
		long unapprovedCouponCount,
		int salary,
		int totalCategorySum,
		int snackSum,
		int learningSum,
		int couponSum,
		int gameSum,
		int etcSum,
		int asset,
		LocalTime transferAlertPeriod,
		LocalTime transferPeriod
	) {
		this.unapprovedCouponCount = unapprovedCouponCount;
		this.salary = salary;
		this.totalCategorySum = totalCategorySum;
		this.snackSum = snackSum;
		this.learningSum = learningSum;
		this.couponSum = couponSum;
		this.gameSum = gameSum;
		this.etcSum = etcSum;
		this.asset = asset;
		this.transferAlertPeriod = transferAlertPeriod;
		this.transferPeriod = transferPeriod;
	}
}

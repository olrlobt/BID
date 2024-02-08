package com.ssafy.bid.domain.user.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentFindResponse {
	private int savingNo;
	private int savingDepositPeriod;
	private int savingInterestRate;
	private int savingCurrentPeriod;
	private int savingCurrentPrice;
	private int savingResultPrice;
	private boolean attendanceMonday;
	private boolean attendanceTuesday;
	private boolean attendanceWednesday;
	private boolean attendanceThursday;
	private boolean attendanceFriday;
	private int ballCount;
	private int totalCategorySum;
	private int snackSum;
	private int learningSum;
	private int couponSum;
	private int gameSum;
	private int etcSum;
	private int totalIncome;
	private int totalExpense;
	private List<UserCouponsFindResponse> couponsResponses;
	private List<AccountsFindResponse> accountsResponses;

	public StudentFindResponse(
		int savingNo,
		int savingDepositPeriod,
		int savingInterestRate,
		LocalDateTime savingStartPeriod,
		int savingDepositPrice,
		int savingCurrentPrice,
		Boolean attendanceMonday,
		Boolean attendanceTuesday,
		Boolean attendanceWednesday,
		Boolean attendanceThursday,
		Boolean attendanceFriday,
		Integer ballCount,
		Integer snackSum,
		Integer learningSum,
		Integer couponSum,
		Integer gameSum,
		Integer etcSum,
		int totalIncome,
		int totalExpense
	) {
		this.savingNo = savingNo;
		this.savingDepositPeriod = savingDepositPeriod;
		this.savingInterestRate = savingInterestRate;
		if (savingStartPeriod != null) {
			this.savingCurrentPeriod = (int)ChronoUnit.DAYS.between(savingStartPeriod, LocalDateTime.now());
		}
		this.savingCurrentPrice = savingCurrentPrice;
		this.savingResultPrice =
			savingResultPrice + (savingDepositPeriod - savingCurrentPeriod) * savingDepositPrice * (1
				+ savingInterestRate / 100);
		this.attendanceMonday = attendanceMonday;
		this.attendanceTuesday = attendanceTuesday;
		this.attendanceWednesday = attendanceWednesday;
		this.attendanceThursday = attendanceThursday;
		this.attendanceFriday = attendanceFriday;
		this.ballCount = ballCount;
		this.snackSum = snackSum;
		this.learningSum = learningSum;
		this.couponSum = couponSum;
		this.gameSum = gameSum;
		this.etcSum = etcSum;
		this.totalCategorySum = snackSum + learningSum + couponSum + gameSum + etcSum;
		this.totalIncome = totalIncome;
		this.totalExpense = totalExpense;
	}

	public void completeResponse(
		List<UserCouponsFindResponse> couponsResponses,
		List<AccountsFindResponse> accountsResponses
	) {
		this.couponsResponses = couponsResponses;
		this.accountsResponses = accountsResponses;
	}
}

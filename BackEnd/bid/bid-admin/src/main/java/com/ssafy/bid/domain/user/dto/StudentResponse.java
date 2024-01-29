package com.ssafy.bid.domain.user.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentResponse {
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
	private List<UserCouponsResponse> couponsResponses;
	private List<AccountsResponse> accountsResponses;

	public StudentResponse(
		int savingNo,
		int savingDepositPeriod,
		int savingInterestRate,
		LocalDateTime savingStartPeriod,
		int savingDepositPrice,
		int savingResultPrice,
		boolean attendanceMonday,
		boolean attendanceTuesday,
		boolean attendanceWednesday,
		boolean attendanceThursday,
		boolean attendanceFriday,
		int ballCount
	) {
		this.savingNo = savingNo;
		this.savingDepositPeriod = savingDepositPeriod;
		this.savingInterestRate = savingInterestRate;
		this.savingCurrentPeriod = (int)ChronoUnit.DAYS.between(savingStartPeriod, LocalDateTime.now());
		this.savingCurrentPrice = savingDepositPrice * savingCurrentPeriod;
		this.savingResultPrice = savingResultPrice;
		this.attendanceMonday = attendanceMonday;
		this.attendanceTuesday = attendanceTuesday;
		this.attendanceWednesday = attendanceWednesday;
		this.attendanceThursday = attendanceThursday;
		this.attendanceFriday = attendanceFriday;
		this.ballCount = ballCount;
	}

	public void addSnackSum(int snackSum) {
		this.totalCategorySum += snackSum;
		this.snackSum += snackSum;
	}

	public void addLearningSum(int learningSum) {
		this.totalCategorySum += learningSum;
		this.learningSum += learningSum;
	}

	public void addCouponSum(int couponSum) {
		this.totalCategorySum += couponSum;
		this.couponSum += couponSum;
	}

	public void addGameSum(int gameSum) {
		this.totalCategorySum += gameSum;
		this.gameSum += gameSum;
	}

	public void addEtcSum(int etcSum) {
		this.totalCategorySum += etcSum;
		this.etcSum += etcSum;
	}

	public void addTotalIncome(int totalIncome) {
		this.totalIncome += totalIncome;
	}

	public void addTotalExpense(int totalExpense) {
		this.totalExpense += totalExpense;
	}
}

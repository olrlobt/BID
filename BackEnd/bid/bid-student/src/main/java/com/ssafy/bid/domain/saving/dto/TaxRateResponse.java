package com.ssafy.bid.domain.saving.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaxRateResponse {

	private int salary;
	private int avgGradeIncome;
	private int sumMyIncome;
	private int incomeLevel;

	public TaxRateResponse(
		int salary,
		double avgGradeIncome,
		int sumMyIncome
	) {
		this.salary = salary;
		this.avgGradeIncome = (int)avgGradeIncome;
		this.sumMyIncome = sumMyIncome;
		this.incomeLevel = calculateIncomeLevel();
	}

	public int calculateIncomeLevel() {
		if (salary / 5 * 4 >= sumMyIncome) {
			return 0;
		} else if (salary / 5 * 4 < sumMyIncome && avgGradeIncome / 5 >= sumMyIncome) {
			return 1;
		} else if (avgGradeIncome / 5 < sumMyIncome && avgGradeIncome / 4 >= sumMyIncome) {
			return 2;
		} else if (avgGradeIncome / 4 < sumMyIncome && avgGradeIncome / 3 >= sumMyIncome) {
			return 3;
		} else if (avgGradeIncome / 3 < sumMyIncome && avgGradeIncome / 2 >= sumMyIncome) {
			return 4;
		} else if (avgGradeIncome / 2 < sumMyIncome && avgGradeIncome >= sumMyIncome) {
			return 5;
		} else {
			return 6;
		}
	}

	public TaxRateListGetResponse createZero() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(0)
			.startRange(0)
			.endRange(salary / 5 * 4)
			.taxRate(3)
			.build();
	}

	public TaxRateListGetResponse createOne() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(1)
			.startRange(salary / 5 * 4)
			.endRange(avgGradeIncome / 5)
			.taxRate(5)
			.build();
	}

	public TaxRateListGetResponse createTwo() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(2)
			.startRange(avgGradeIncome / 5)
			.endRange(avgGradeIncome / 4)
			.taxRate(7)
			.build();
	}

	public TaxRateListGetResponse createThree() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(3)
			.startRange(avgGradeIncome / 4)
			.endRange(avgGradeIncome / 3)
			.taxRate(9)
			.build();
	}

	public TaxRateListGetResponse createFour() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(4)
			.startRange(avgGradeIncome / 3)
			.endRange(avgGradeIncome / 2)
			.taxRate(11)
			.build();
	}

	public TaxRateListGetResponse createFive() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(5)
			.startRange(avgGradeIncome / 2)
			.endRange(avgGradeIncome)
			.taxRate(13)
			.build();
	}

	public TaxRateListGetResponse createSix() {
		return TaxRateListGetResponse.builder()
			.incomeLevel(6)
			.startRange(avgGradeIncome)
			.endRange(0)
			.taxRate(15)
			.build();
	}
}

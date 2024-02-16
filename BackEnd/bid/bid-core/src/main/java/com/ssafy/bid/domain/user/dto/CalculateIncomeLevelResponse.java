package com.ssafy.bid.domain.user.dto;

import com.ssafy.bid.domain.user.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CalculateIncomeLevelResponse {
	private Student student;
	private int salary;
	private int avgGradeIncome;
	private int sumMyIncome;
	private int taxRate;

	public CalculateIncomeLevelResponse(
		Student student,
		int salary,
		double avgGradeIncome,
		int sumMyIncome
	) {
		this.student = student;
		this.salary = salary;
		this.avgGradeIncome = (int)avgGradeIncome;
		this.sumMyIncome = sumMyIncome;
	}

	public void calculateIncomeLevel() {
		if (salary / 5 * 4 >= sumMyIncome) {
			this.taxRate = 3;
		} else if (salary / 5 * 4 < sumMyIncome && avgGradeIncome / 5 >= sumMyIncome) {
			this.taxRate = 5;
		} else if (avgGradeIncome / 5 < sumMyIncome && avgGradeIncome / 4 >= sumMyIncome) {
			this.taxRate = 7;
		} else if (avgGradeIncome / 4 < sumMyIncome && avgGradeIncome / 3 >= sumMyIncome) {
			this.taxRate = 9;
		} else if (avgGradeIncome / 3 < sumMyIncome && avgGradeIncome / 2 >= sumMyIncome) {
			this.taxRate = 11;
		} else if (avgGradeIncome / 2 < sumMyIncome && avgGradeIncome >= sumMyIncome) {
			this.taxRate = 13;
		} else {
			this.taxRate = 15;
		}
	}
}

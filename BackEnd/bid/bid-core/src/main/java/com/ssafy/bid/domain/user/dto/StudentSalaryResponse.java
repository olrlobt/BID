package com.ssafy.bid.domain.user.dto;

import com.ssafy.bid.domain.user.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentSalaryResponse {
	private Student student;
	private int salary;

	public StudentSalaryResponse(
		Student student,
		int salary
	) {
		this.student = student;
		this.salary = salary;
	}

	public int calculateSalary(int salary) {
		return this.student.calculateSalary(salary);
	}
}

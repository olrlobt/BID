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

	public void calculateSalary(int salary) {
		this.student.calculateSalary(salary);
	}
}

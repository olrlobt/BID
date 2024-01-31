package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodModifyRequest;

public interface GradeService {
	GradeFindResponse findGrade(int gradeNo);

	void modifySalary(int gradeNo, SalaryModifyRequest salaryModifyRequest);

	void modifySavingTime(int gradeNo, SavingPeriodModifyRequest savingPeriodModifyRequest);
}

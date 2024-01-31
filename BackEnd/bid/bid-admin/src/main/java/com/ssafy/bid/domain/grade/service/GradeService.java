package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;

public interface GradeService {
	GradeFindResponse findGrade(int gradeNo);

	void modifySalary(int gradeNo, SalaryModifyRequest salaryModifyRequest);
}

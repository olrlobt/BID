package com.ssafy.bid.domain.grade.service;

import java.util.List;

import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeSaveRequest;
import com.ssafy.bid.domain.grade.dto.SalaryUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodUpdateRequest;

public interface GradeService {
	void saveGrade(GradeSaveRequest request);

	List<GradeListGetResponse> getGrades();

	void deleteGrade(int gradeNo);

	void updateSalary(int gradeNo, SalaryUpdateRequest salaryUpdateRequest);

	void updateSavingPeriod(int gradeNo, SavingPeriodUpdateRequest savingPeriodUpdateRequest);
}

package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.*;

import java.util.List;

public interface GradeService {
    void createGrade(GradeCreationRequest request);
    List<GradeDTO> listGrades();
    void deleteGrade(Integer gradeNo);

	void modifySalary(int gradeNo, SalaryModifyRequest salaryModifyRequest);

	void modifySavingTime(int gradeNo, SavingPeriodModifyRequest savingPeriodModifyRequest);
}

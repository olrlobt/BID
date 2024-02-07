package com.ssafy.bid.domain.grade.service;



import com.ssafy.bid.domain.grade.dto.GradeCreationRequest;
import com.ssafy.bid.domain.grade.dto.GradeDTO;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsFindResponse;
import com.ssafy.bid.domain.grade.dto.SalaryModifyRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodModifyRequest;


import java.util.List;

public interface GradeService {
    void createGrade(GradeCreationRequest request);
    List<GradeDTO> listGrades();
    void deleteGrade(Integer gradeNo);

	GradeStatisticsFindResponse findGrade(int gradeNo);

	void modifySalary(int gradeNo, SalaryModifyRequest salaryModifyRequest);

	void modifySavingTime(int gradeNo, SavingPeriodModifyRequest savingPeriodModifyRequest);
}

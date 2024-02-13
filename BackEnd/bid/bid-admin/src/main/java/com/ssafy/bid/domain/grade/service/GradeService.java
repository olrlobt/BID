package com.ssafy.bid.domain.grade.service;

import java.util.List;

import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeSaveRequest;
import com.ssafy.bid.domain.grade.dto.GradeUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SalaryUpdateRequest;
import com.ssafy.bid.domain.grade.dto.SavingPeriodUpdateRequest;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

public interface GradeService {
	void saveGrade(int userNo, GradeSaveRequest request);

	List<GradeListGetResponse> getGrades(CustomUserInfo userInfo);

	void updateMainGrade(int userNo, GradeUpdateRequest gradeUpdateRequest);

	void deleteGrade(UserType userType, int gradeNo);

	void updateSalary(UserType userType, int gradeNo, SalaryUpdateRequest salaryUpdateRequest);

	void updateSavingPeriod(UserType userType, int gradeNo, SavingPeriodUpdateRequest savingPeriodUpdateRequest);

	void holdBid(int gradeNo);

	void unHoldBid(int gradeNo);
}

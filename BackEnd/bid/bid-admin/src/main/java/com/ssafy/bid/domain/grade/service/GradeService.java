package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeFindResponse;

public interface GradeService {
	GradeFindResponse findGrade(int gradeNo);
}

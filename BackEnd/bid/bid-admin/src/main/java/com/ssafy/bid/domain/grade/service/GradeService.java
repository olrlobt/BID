package com.ssafy.bid.domain.grade.service;

import com.ssafy.bid.domain.grade.dto.GradeCreationRequest;
import com.ssafy.bid.domain.grade.dto.GradeDTO;

import java.util.List;

public interface GradeService {
    void createGrade(GradeCreationRequest request);
    List<GradeDTO> listGrades();
    void deleteGrade(Integer gradeNo);
}

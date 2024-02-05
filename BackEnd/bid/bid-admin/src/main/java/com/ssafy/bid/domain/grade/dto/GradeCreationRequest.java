package com.ssafy.bid.domain.grade.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GradeCreationRequest {
    private String schoolCode;
    private Integer year;
    private Integer classRoom;
    private List<StudentsRegistrationDto> students;
}

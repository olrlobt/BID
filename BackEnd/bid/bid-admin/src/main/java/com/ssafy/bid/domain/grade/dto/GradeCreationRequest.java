package com.ssafy.bid.domain.grade.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class GradeCreationRequest {
    private String schoolCode;
    private int year;
    private int classRoom;
    private List<StudentsRegistrationDto> students;
    private Integer userNo;
}

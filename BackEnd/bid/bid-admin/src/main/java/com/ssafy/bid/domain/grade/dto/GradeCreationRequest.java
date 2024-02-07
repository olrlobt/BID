package com.ssafy.bid.domain.grade.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GradeCreationRequest {
    private String schoolCode;
    private int year;
    private int classRoom;
    private List<StudentsRegistrationDto> students;
    private Integer userNo;
}

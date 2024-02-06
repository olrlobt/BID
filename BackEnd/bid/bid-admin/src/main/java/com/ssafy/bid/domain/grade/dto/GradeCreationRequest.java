package com.ssafy.bid.domain.grade.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class GradeCreationRequest {
	private String schoolCode;
	private Integer year;
	private Integer classRoom;
	private List<StudentsRegistrationDto> students;
}

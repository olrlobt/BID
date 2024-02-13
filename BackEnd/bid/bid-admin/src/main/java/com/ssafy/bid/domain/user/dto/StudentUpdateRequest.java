package com.ssafy.bid.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentUpdateRequest {
	private int schoolNo;
	private int number;
	private String birthDate;
	private String name;
	private int gradeNo;
}

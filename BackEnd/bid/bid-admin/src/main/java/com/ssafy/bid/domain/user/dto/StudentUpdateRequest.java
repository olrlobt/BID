package com.ssafy.bid.domain.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;
import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;

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

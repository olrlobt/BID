package com.ssafy.bid.domain.grade.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.user.Student;

public class StudentsRegistrationDto {
	private String id;
	private String password;
	private String name;
	private String birthDate;
	private Integer gradeNo;

	public Student toEntity(PasswordEncoder passwordEncoder, Integer schoolNo) {
		return new Student(id, passwordEncoder.encode(password), name, schoolNo, birthDate, 0, 0, null, gradeNo);
	}
}

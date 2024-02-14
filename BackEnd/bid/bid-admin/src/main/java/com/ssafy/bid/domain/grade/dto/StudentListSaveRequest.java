package com.ssafy.bid.domain.grade.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;
import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;

import lombok.Getter;

@Getter
public class StudentListSaveRequest {
	private String password;
	private String name;
	private String birthDate;
	private int number;

	public Student toEntity(PasswordEncoder passwordEncoder, int schoolNo, int gradeNo, String id) {
		return new Student(
			id + String.format("%02d", this.number),
			passwordEncoder.encode(password),
			name,
			schoolNo,
			birthDate,
			0,
			1,
			"https://ssafya306.s3.ap-northeast-2.amazonaws.com/DefaultBody.png",
			new Attendance(),
			new ExpenditureStatistics(),
			3,
			gradeNo
		);
	}
}

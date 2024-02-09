package com.ssafy.bid.domain.grade.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;
import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;

import lombok.Getter;

@Getter
public class StudentListSaveRequest {
	private String id;
	private String password;
	private String name;
	private String birthDate;

	public Student toEntity(PasswordEncoder passwordEncoder, int schoolNo, int gradeNo) {
		return Student.builder()
			.id(id)
			.password(passwordEncoder.encode(password))
			.name(name)
			.schoolNo(schoolNo)
			.birthDate(birthDate)
			.asset(0)
			.ballCount(1)
			.profileImgUrl("url") //TODO: 회원프로필이미지 디폴트 url로 대체하기
			.attendance(new Attendance())
			.expenditureStatistics(new ExpenditureStatistics())
			.gradeNo(gradeNo)
			.build();
	}
}

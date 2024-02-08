package com.ssafy.bid.domain.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;
import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StudentSaveRequest {
	private int schoolNo;
	private String id;
	private String password;
	private String name;
	private int gradeNo;

	public Student toEntity(PasswordEncoder passwordEncoder) {
		return Student.builder()
			.id(id)
			.password(passwordEncoder.encode(password))
			.name(name)
			.schoolNo(schoolNo)
			.birthDate(password)
			.asset(0)
			.ballCount(1)
			.profileImgUrl("url") //TODO: 회원프로필이미지 디폴트 url로 대체하기
			.attendance(new Attendance())
			.expenditureStatistics(new ExpenditureStatistics())
			.gradeNo(gradeNo)
			.build();
	}
}

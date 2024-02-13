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
	private int number;
	private String password;
	private String name;
	private int gradeNo;

	public Student toEntity(PasswordEncoder passwordEncoder, String studentId) {
		return Student.builder()
			.id(studentId)
			.password(passwordEncoder.encode(password))
			.name(name)
			.schoolNo(schoolNo)
			.birthDate(password)
			.asset(0)
			.ballCount(1)
			.profileImgUrl("https://ssafya306.s3.ap-northeast-2.amazonaws.com/DefaultBody.png")
			.attendance(new Attendance())
			.expenditureStatistics(new ExpenditureStatistics())
			.gradeNo(gradeNo)
			.build();
	}
}

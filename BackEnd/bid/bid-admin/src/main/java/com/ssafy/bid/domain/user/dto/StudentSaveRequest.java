package com.ssafy.bid.domain.user.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

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

	//TODO: SuperBuilder 등 적용 방안 적용, profileImgUrl 적용
	public Student toEntity(PasswordEncoder passwordEncoder) {
		return new Student(
			this.id, passwordEncoder.encode(this.password), this.name, this.schoolNo, password,
			0, 0, null, this.gradeNo
		);
	}
}

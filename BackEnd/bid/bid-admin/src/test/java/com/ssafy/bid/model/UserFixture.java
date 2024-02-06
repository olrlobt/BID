package com.ssafy.bid.model;

import com.ssafy.bid.domain.user.Student;

public class UserFixture {

	public static Student create(String id) {
		return Student.builder()
			.id(id)
			.password("990625")
			.name("유현지")
			.schoolNo(1)
			.birthDate("990625")
			.asset(500000)
			.ballCount(6)
			.profileImgUrl("url")
			.gradeNo(1)
			.build();
	}
}

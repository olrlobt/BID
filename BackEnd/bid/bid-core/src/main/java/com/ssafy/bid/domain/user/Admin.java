package com.ssafy.bid.domain.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("admin")
@Entity
public class Admin extends User {

	private String tel;

	private Integer mainGradeNo;

	public Admin(
		String id,
		String password,
		String name,
		Integer schoolNo,
		String tel,
		Integer mainGradeNo
	) {

		super(id, password, name, schoolNo);
		this.tel = tel;
		this.mainGradeNo = mainGradeNo;
	}

	public void update(String name, int schoolNo, String tel) {
		super.updateAdmin(name, schoolNo);
		this.tel = tel;
	}

	public void alterMainGrade(int mainGradeNo) {
		this.mainGradeNo = mainGradeNo;
	}
}

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

	public Admin(
		String id,
		String password,
		String name,
		Integer schoolNo,
		String tel
	) {

		super(id, password, name, schoolNo);
		this.tel = tel;
	}

	public void update(String name, int schoolNo, String tel) {
		super.updateAdmin(name, schoolNo);
		this.tel = tel;
	}
}

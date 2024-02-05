package com.ssafy.bid.domain.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("admin")
@SuperBuilder
@Entity
public class Admin extends User {

	private String tel;
	public Admin(String id, String password, String name, Integer schoolNo, String tel) {
		super(id, password, name, schoolNo);
		this.tel = tel;
	}
}

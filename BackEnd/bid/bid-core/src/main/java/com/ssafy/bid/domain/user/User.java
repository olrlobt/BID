package com.ssafy.bid.domain.user;

import com.ssafy.bid.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Table(name = "users")
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private Integer no;

	@NotNull
	private String id;

	@NotNull
	private String password;

	@NotNull
	private String name;

	/**
	 * school : users(me) = 1 : N
	 */
	@NotNull
	private Integer schoolNo;

	@Builder
	public User(
		String id,
		String password,
		String name,
		Integer schoolNo
	) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.schoolNo = schoolNo;
	}

	public void changePassword(String encodedNewPassword) {
		this.password = encodedNewPassword;
	}
}

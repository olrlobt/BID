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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
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

	private String profileImgUrl;

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

	public User(
		String id,
		String password,
		String name,
		Integer schoolNo,
		String profileImgUrl
	) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.schoolNo = schoolNo;
		this.profileImgUrl = profileImgUrl;
	}

	public void updateAdmin(String name, int schoolNo) {
		this.name = name;
		this.schoolNo = schoolNo;
	}

	public void updateProfileImgUrl(String profileImgUrl) {
		this.profileImgUrl = profileImgUrl;
	}

	public void changePassword(String encodedNewPassword) {
		this.password = encodedNewPassword;
	}

	public void updateInfo(String newId, String newName) {
		this.id = newId;
		this.name = newName;
	}
}

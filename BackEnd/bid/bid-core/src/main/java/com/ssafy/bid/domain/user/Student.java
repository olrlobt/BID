package com.ssafy.bid.domain.user;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DiscriminatorValue("student")
@Entity
public class Student extends User {

	private String birthDate;

	@ColumnDefault("0")
	private Integer asset;

	@ColumnDefault("0")
	private Integer ballCount;

	private String profileImgUrl;

	@Embedded
	private Attendance attendance;

	/**
	 * grade : users(me) = 1 : N
	 */
	private Integer gradeNo;

	@Builder
	public Student(
		String id,
		String password,
		String name,
		Integer schoolNo,
		String birthDate,
		Integer asset,
		Integer ballCount,
		String profileImgUrl,
		Integer gradeNo
	) {
		super(id, password, name, schoolNo);
		this.birthDate = birthDate;
		this.asset = asset;
		this.ballCount = ballCount;
		this.profileImgUrl = profileImgUrl;
		this.gradeNo = gradeNo;
	}

	public void addRewardPrice(int price) {
		this.asset += price;
	}

	public void modifyProfileImgUrl(String url) {
		this.profileImgUrl = url;
	}

	public void subtractSavingPrice(int price) {
		this.asset -= price;
	}

	public void addSavingPrice(int price) {
		this.asset += price;
	}
}

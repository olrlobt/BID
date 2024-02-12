package com.ssafy.bid.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DiscriminatorValue("student")
@Entity
public class Student extends User {

	private String birthDate;

	private Integer asset;

	private Integer ballCount;

	private String profileImgUrl;

	@Embedded
	private Attendance attendance;

	@Embedded
	private ExpenditureStatistics expenditureStatistics;

	private Integer taxRate;

	/**
	 * grade : users(me) = 1 : N
	 */
	private Integer gradeNo;

	public void addRewardPrice(int price) {
		this.asset += price - price / taxRate;
	}

	public void updateAvatar(String url) {
		this.profileImgUrl = url;
	}

	public void subtractSavingPrice(int price) {
		this.asset -= price;
	}

	public void addSavingPrice(int price) {
		this.asset += price;
	}

	public void resetBalls() {
		this.ballCount = 1;
	}

	public void checkAttendance() {
		this.attendance.checkAttendance();
	}

	public void calculateSalary(int salary) {
		this.asset += this.attendance.calculateSalary(salary) - this.attendance.calculateSalary(salary) / taxRate;
	}

	public void resetPassword(PasswordEncoder passwordEncoder) {
		super.changePassword(passwordEncoder.encode(this.birthDate));
	}

	public void calculateTaxRate(int taxRate) {
		this.taxRate = taxRate + 7; // 소득세 + VAT
	}
}

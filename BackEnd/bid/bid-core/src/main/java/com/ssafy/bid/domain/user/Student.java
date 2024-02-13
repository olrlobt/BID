package com.ssafy.bid.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.bid.domain.grade.ExpenditureStatistics;

import jakarta.persistence.Column;
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

	@Column(columnDefinition = "TEXT")
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

	public Student(
		String id,
		String password,
		String name,
		Integer schoolNo,
		String birthDate,
		Integer asset,
		Integer ballCount,
		String profileImgUrl,
		Attendance attendance,
		ExpenditureStatistics expenditureStatistics,
		Integer taxRate,
		Integer gradeNo
	) {
		super(id, password, name, schoolNo, profileImgUrl);
		this.birthDate = birthDate;
		this.asset = asset;
		this.ballCount = ballCount;
		this.profileImgUrl = profileImgUrl;
		this.attendance = attendance;
		this.expenditureStatistics = expenditureStatistics;
		this.taxRate = taxRate;
		this.gradeNo = gradeNo;
	}

	public int addPrice(int price) {
		this.asset += price - price / taxRate;
		return price - price / taxRate;
	}

	public void updateAvatar(String profileImgUrl) {
		super.updateProfileImgUrl(profileImgUrl);
	}

	public void subtractPrice(int price) {
		this.asset -= price;
	}

	public void resetBalls() {
		this.ballCount = 1;
	}

	public void checkAttendance() {
		this.attendance.checkAttendance();
	}

	public int calculateSalary(int salary) {
		this.asset += this.attendance.calculateSalary(salary) - this.attendance.calculateSalary(salary) / taxRate;
		return this.attendance.calculateSalary(salary) - this.attendance.calculateSalary(salary) / taxRate;
	}

	public void resetPassword(PasswordEncoder passwordEncoder) {
		super.changePassword(passwordEncoder.encode(this.birthDate));
	}

	public void calculateTaxRate(int taxRate) {
		this.taxRate = taxRate + 7; // 소득세 + VAT
	}

	public void updateStudentInfo(String newId, String newName, String newBirthDate, PasswordEncoder passwordEncoder) {
		super.updateInfo(newId, newName);
		super.changePassword(passwordEncoder.encode(newBirthDate));
		this.birthDate = newBirthDate;
	}

	public void addBall() {
		this.ballCount += 1;
	}
}

package com.ssafy.bid.domain.grade;

import java.time.LocalTime;

import com.ssafy.bid.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "grade")
@Entity
public class Grade extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grade_no")
	private Integer no;

	@NotNull
	private Integer asset;

	@NotNull
	private String schoolCode;

	@NotNull
	private Integer year;

	@NotNull
	private Integer classRoom;

	@NotNull
	private Integer salary;

	@NotNull
	private LocalTime transferAlertPeriod;

	@NotNull
	private LocalTime transferPeriod;

	/**
	 * users : grade(me) = 1 : N
	 */
	@NotNull
	private Integer userNo;

	public Grade(String schoolCode, Integer year, Integer classRoom) {
		this.schoolCode = schoolCode;
		this.year = year;
		this.classRoom = classRoom;

	@Embedded
	private ExpenditureStatistics expenditureStatistics;

	@Embedded
	private BiddingStatistics biddingStatistics;

	public void modifySalary(int salary) {
		this.salary = salary;
	}

	public void modifySavingTime(LocalTime transferAlertPeriod, LocalTime transferPeriod) {
		this.transferAlertPeriod = transferAlertPeriod;
		this.transferPeriod = transferPeriod;
	}
}

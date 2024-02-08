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
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
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

	@Embedded
	private ExpenditureStatistics expenditureStatistics;

	@Embedded
	private BiddingStatistics biddingStatistics;

	public void updateSalary(int salary) {
		this.salary = salary;
	}

	public void updateSavingPeriod(
		LocalTime transferAlertPeriod,
		LocalTime transferPeriod
	) {
		this.transferAlertPeriod = transferAlertPeriod;
		this.transferPeriod = transferPeriod;
	}
}

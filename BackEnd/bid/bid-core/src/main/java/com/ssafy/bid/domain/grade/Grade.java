package com.ssafy.bid.domain.grade;

import java.time.LocalTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@DynamicInsert
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

	@NotNull
	private boolean hold;

	private Integer salaryRecommendation;

	@ColumnDefault("false")
	private Boolean isDangerInInflation;

	@ColumnDefault("false")
	private Boolean isDangerInDeflation;

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

	public void holdBid() {
		log.info("hold");
		this.hold = true;
	}

	public void unHoldBid() {
		log.info("unhold");
		this.hold = false;
	}

	public boolean holdBidToggle() {
		return this.hold = !hold;
	}

	public void updateSalaryRecommendation(int diff) {
		int count = diff / 20;
		this.salaryRecommendation = count == 0 ? this.salary : this.salary - this.salary / (10 * count);
		if (count >= 4) {
			isDangerInInflation = true;
		} else if (count <= -4) {
			isDangerInDeflation = true;
		}
	}
}

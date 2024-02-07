package com.ssafy.bid.domain.gradeperiod;

import java.time.LocalTime;

import jakarta.persistence.Column;
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
@Table(name = "grade_period")
@Entity
public class GradePeriod {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "grade_period_no")
	private Integer no;

	@NotNull
	private Integer sequence;

	@NotNull
	private LocalTime startPeriod;

	@NotNull
	private LocalTime endPeriod;

	/**
	 * grade : gradePeriod(me) = 1 : N
	 */
	@NotNull
	private Integer gradeNo;

	public void update(
		LocalTime startPeriod,
		LocalTime endPeriod
	) {
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
	}
}

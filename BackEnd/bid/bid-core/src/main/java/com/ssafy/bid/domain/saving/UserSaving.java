package com.ssafy.bid.domain.saving;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_saving")
@Entity
public class UserSaving {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_saving_no")
	private Integer no;

	@NotNull
	private LocalDateTime startPeriod;

	@NotNull
	private LocalDateTime endPeriod;

	@NotNull
	private Integer currentPrice;

	/**
	 * users : userSaving(me) = 1 : 1
	 */
	@NotNull
	private Integer userNo;

	/**
	 * saving : userSaving(me) = 1 : N
	 */
	@NotNull
	private Integer savingNo;

	/**
	 * grade : userSaving(me) = 1 : N
	 */
	@NotNull
	private Integer gradeNo;

	@Builder
	public UserSaving(
		LocalDateTime startPeriod,
		LocalDateTime endPeriod,
		Integer currentPrice,
		Integer userNo,
		Integer savingNo
	) {
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.currentPrice = currentPrice;
		this.userNo = userNo;
		this.savingNo = savingNo;
	}
}

package com.ssafy.bid.domain.saving;

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
@Table(name = "saving")
@Entity
public class Saving {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saving_no")
	private Integer no;

	@NotNull
	private String name;

	@NotNull
	private Integer depositPeriod;

	@NotNull
	private Integer depositCycle;

	@NotNull
	private Integer depositPrice;

	@NotNull
	private Integer interestRate;

	@NotNull
	private String terms;

	/**
	 * grade : saving = 1 : N
	 */
	@NotNull
	private Integer gradeNo;

	@Builder
	public Saving(
		String name,
		Integer depositPeriod,
		Integer depositCycle,
		Integer depositPrice,
		Integer interestRate,
		Integer gradeNo
	) {
		this.name = name;
		this.depositPeriod = depositPeriod;
		this.depositCycle = depositCycle;
		this.depositPrice = depositPrice;
		this.interestRate = interestRate;
		this.terms = "매일 1교시 시작 전 이체 알림이 발송되고, 6교시 종료 전 자동으로 이체돼요\n"
			+ "이체될 때 가지고 있는 비드가 부족하면 적금은 자동으로 해지돼요";
		this.gradeNo = gradeNo;
	}

	public void modify(int depositPrice, int interestRate) {
		this.depositPrice = depositPrice;
		this.interestRate = interestRate;
	}
}

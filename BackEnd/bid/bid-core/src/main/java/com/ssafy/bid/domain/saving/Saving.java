package com.ssafy.bid.domain.saving;

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

	public void modify(int depositPrice, int interestRate) {
		this.depositPrice = depositPrice;
		this.interestRate = interestRate;
	}
}

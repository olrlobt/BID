package com.ssafy.bid.domain.reward;

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
@Table(name = "reward")
@Entity
public class Reward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reward_no")
	private Integer no;

	@NotNull
	private String name;

	@NotNull
	private Integer price;

	/**
	 * grade : reward(me) = N : 1
	 */
	@NotNull
	private  Integer gradeNo;

	@Builder
	public Reward(
		String name,
		Integer price,
		Integer gradeNo
	) {
		this.name = name;
		this.price = price;
		this.gradeNo = gradeNo;
	}
}

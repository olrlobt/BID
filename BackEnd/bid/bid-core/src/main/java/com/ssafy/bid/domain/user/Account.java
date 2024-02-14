package com.ssafy.bid.domain.user;

import com.ssafy.bid.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "account")
@Entity
public class Account extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_no")
	private Long no;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@NotNull
	private Integer price;

	@NotNull
	private String content;

	@NotNull
	@Enumerated(EnumType.STRING)
	private DealType dealType;

	/**
	 * users : account(me) = 1 : N
	 */
	@NotNull
	private Integer userNo;

	/**
	 * grade : account(me) = 1 : N
	 */
	@NotNull
	private Integer gradeNo;
}

package com.ssafy.bid.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_coupon")
@Entity
public class UserCoupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_coupon_no")
	private Long no;

	@Enumerated(EnumType.STRING)
	private UsageStatus useStatus;

	/**
	 * coupon : userCoupon(me) = 1 : N
	 */
	private Integer couponNo;

	/**
	 * users : userCoupon(me) = 1 : N
	 */
	private Integer userNo;
}

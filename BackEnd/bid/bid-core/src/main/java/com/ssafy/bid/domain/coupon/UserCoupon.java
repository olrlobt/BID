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
import lombok.Builder;
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

	/**
	 * grade : userCoupon(me) = 1 : N
	 */
	private Integer gradeNo;

	@Builder
	private UserCoupon(Integer couponNo, Integer userNo, Integer gradeNo) {
		this.couponNo = couponNo;
		this.userNo = userNo;
		this.gradeNo = gradeNo;
	}

	public void reject() {
		this.useStatus = UsageStatus.BEFORE_USE;
	}

	public void request() {
		this.useStatus = UsageStatus.REQUEST_PROGRESS;
	}
}

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

	/**
	 * grade : userCoupon(me) = 1 : N
	 */
	private Integer gradeNo;

	public void reject() {
		this.useStatus = UsageStatus.BEFORE_USE;
	}

	public UserCoupon(Long no, UsageStatus useStatus, Integer couponNo, Integer userNo, Integer gradeNo) {
		this.no = no;
		this.useStatus = useStatus;
		this.couponNo = couponNo;
		this.userNo = userNo;
		this.gradeNo = gradeNo;
	}
}

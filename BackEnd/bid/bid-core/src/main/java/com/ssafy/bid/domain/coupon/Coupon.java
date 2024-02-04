package com.ssafy.bid.domain.coupon;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
@Entity
public class Coupon extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_no")
	private Integer no;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CouponStatus couponStatus;

	@NotNull
	private Integer startPrice;

	/**
	 * grade : coupon(me) = 1 : N
	 */
	@NotNull
	private Integer gradeNo;

	public void register() {
		this.couponStatus = CouponStatus.REGISTERED;
	}

	public void unRegister() {
		this.couponStatus = CouponStatus.UNREGISTERED;
	}
}

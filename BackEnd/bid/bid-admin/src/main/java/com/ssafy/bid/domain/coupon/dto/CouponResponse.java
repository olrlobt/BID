package com.ssafy.bid.domain.coupon.dto;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponResponse {
	private Integer no;
	private String name;
	private String description;
	private Integer gradeNo;
	private Integer startPrice;
	private CouponStatus couponStatus;

	@Builder
	public CouponResponse(Integer no, String name, String description, Integer gradeNo, Integer startPrice,
		CouponStatus couponStatus) {
		this.no = no;
		this.name = name;
		this.description = description;
		this.gradeNo = gradeNo;
		this.startPrice = startPrice;
		this.couponStatus = couponStatus;
	}

	public static CouponResponse to(Coupon coupon){
		return CouponResponse.builder()
			.no(coupon.getNo())
			.name(coupon.getName())
			.description(coupon.getDescription())
			.gradeNo(coupon.getGradeNo())
			.startPrice(coupon.getStartPrice())
			.couponStatus(coupon.getCouponStatus())
			.build();
	}
}

package com.ssafy.bid.domain.coupon.dto;

import com.ssafy.bid.domain.coupon.Coupon;
import com.ssafy.bid.domain.coupon.CouponStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponCreateRequest {

	private String name;
	private String description;
	private int startPrice;
	private int gradeNo;

	@Builder
	private CouponCreateRequest(String name, String content, int startPrice) {
		this.name = name;
		this.description = content;
		this.startPrice = startPrice;
	}

	public Coupon toEntity() {
		return Coupon.builder()
			.name(this.name)
			.description(this.description)
			.startPrice(this.startPrice)
			.couponStatus(CouponStatus.UNREGISTERED)
			.gradeNo(this.gradeNo)
			.build();
	}
}

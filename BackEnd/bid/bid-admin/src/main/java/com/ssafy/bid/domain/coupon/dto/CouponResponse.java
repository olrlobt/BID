package com.ssafy.bid.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CouponResponse {
	private Integer no;
	private String name;
	private String description;
	private Integer gradeNo;
}

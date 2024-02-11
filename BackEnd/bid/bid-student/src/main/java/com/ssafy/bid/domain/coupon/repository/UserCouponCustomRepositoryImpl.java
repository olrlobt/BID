package com.ssafy.bid.domain.coupon.repository;

import static com.ssafy.bid.domain.coupon.QCoupon.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCouponCustomRepositoryImpl implements UserCouponCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<UserCouponResponse> findUserCoupons(int userNo) {

		return queryFactory.select(Projections.constructor(UserCouponResponse.class,
				coupon.no,
				coupon.name,
				coupon.description,
				userCoupon.count()
			))
			.from(coupon)
			.innerJoin(userCoupon)
			.on(coupon.no.eq(userCoupon.couponNo)
				.and(userCoupon.userNo.eq(userNo))
				.and(userCoupon.useStatus.eq(UsageStatus.BEFORE_USE)))
			.groupBy(userCoupon.couponNo)
			.fetch();
	}
}

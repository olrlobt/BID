package com.ssafy.bid.domain.coupon.repository;

import static com.ssafy.bid.domain.coupon.QCoupon.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.coupon.dto.UserCouponResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCouponRepositoryImpl implements UserCouponCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<UserCouponResponse> findUserCoupons(int gradeNo) {

		return queryFactory.select(Projections.constructor(UserCouponResponse.class,
				userCoupon.no,
				student.no,
				student.name,
				coupon.no,
				coupon.name
			))
			.from(userCoupon)
			.innerJoin(student)
			.on(userCoupon.userNo.eq(student.no))
			.where(student.gradeNo.eq(gradeNo))
			.innerJoin(coupon)
			.on(userCoupon.couponNo.eq(coupon.no))
			.where(userCoupon.useStatus.eq(UsageStatus.REQUEST_PROGRESS))
			.fetch();

	}
}

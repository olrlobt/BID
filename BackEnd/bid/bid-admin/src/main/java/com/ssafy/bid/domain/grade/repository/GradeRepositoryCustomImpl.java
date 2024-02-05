package com.ssafy.bid.domain.grade.repository;

import static com.ssafy.bid.domain.board.QBidding.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.gradeperiod.QGradePeriod.*;
import static com.ssafy.bid.domain.user.QAccount.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.BiddingStatus;
import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.grade.dto.AccountsStatisticsResponse;
import com.ssafy.bid.domain.grade.dto.BiddingsStatisticsResponse;
import com.ssafy.bid.domain.grade.dto.GradeFindResponse;
import com.ssafy.bid.domain.grade.dto.GradePeriodsFindResponse;
import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GradeRepositoryCustomImpl implements GradeRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BiddingsStatisticsResponse> findBiddings(int gradeNo) {
		DateTemplate<String> formattedDate = Expressions.dateTemplate(
			String.class,
			"DATE_FORMAT({0}, {1})",
			bidding.createdAt,
			ConstantImpl.create("%Y-%m-%d")
		);

		return queryFactory
			.select(Projections.constructor(BiddingsStatisticsResponse.class,
					formattedDate,
					bidding.no.count()
				)
			)
			.from(bidding)
			.innerJoin(grade).on(grade.no.eq(bidding.gradeNo))
			.where(
				bidding.gradeNo.eq(gradeNo),
				bidding.biddingStatus.eq(BiddingStatus.WINNING_BID),
				bidding.createdAt.goe(LocalDateTime.now().minusWeeks(2)),
				bidding.createdAt.loe(LocalDateTime.now())
			)
			.groupBy(formattedDate)
			.fetch();
	}

	@Override
	public List<GradePeriodsFindResponse> findGradePeriods(int gradeNo) {
		return queryFactory
			.select(Projections.constructor(GradePeriodsFindResponse.class,
					gradePeriod.no,
					gradePeriod.sequence,
					gradePeriod.startPeriod,
					gradePeriod.endPeriod
				)
			)
			.from(gradePeriod)
			.where(gradePeriod.gradeNo.eq(gradeNo))
			.orderBy(gradePeriod.sequence.asc())
			.fetch();
	}

	@Override
	public Optional<GradeFindResponse> findGrade(int gradeNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(GradeFindResponse.class,
						ExpressionUtils.as(
							JPAExpressions
								.select(userCoupon.no.count())
								.from(userCoupon)
								.where(userCoupon.gradeNo.eq(gradeNo))
								.groupBy(userCoupon.useStatus)
								.having(userCoupon.useStatus.eq(UsageStatus.REQUEST_PROGRESS))
							, "unapprovedCouponCount"
						),
						grade.salary,
						grade.asset,
						grade.transferAlertPeriod,
						grade.transferPeriod
					)
				)
				.from(grade)
				.where(
					grade.no.eq(gradeNo),
					grade.deletedAt.isNull()
				)
				.fetchOne()
		);
	}

	@Override
	public List<AccountsStatisticsResponse> findGradeStatistics(int gradeNo) {
		return queryFactory
			.select(Projections.constructor(AccountsStatisticsResponse.class,
					account.dealType,
					account.price.sum()
				)
			)
			.from(account)
			.where(
				account.gradeNo.eq(gradeNo),
				account.accountType.eq(AccountType.EXPENDITURE),
				account.dealType.notIn(DealType.REWARD, DealType.SALARY, DealType.SAVING)
			)
			.groupBy(account.dealType)
			.fetch();
	}
}

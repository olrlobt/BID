package com.ssafy.bid.domain.grade.repository;

import static com.ssafy.bid.domain.board.QBidding.*;
import static com.ssafy.bid.domain.coupon.QUserCoupon.*;
import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.user.QAccount.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.BiddingStatus;
import com.ssafy.bid.domain.coupon.UsageStatus;
import com.ssafy.bid.domain.grade.dto.ExpenditureStatisticsGetResponse;
import com.ssafy.bid.domain.grade.dto.GradeBiddingAvgPriceResponse;
import com.ssafy.bid.domain.grade.dto.GradeStatisticsGetResponse;
import com.ssafy.bid.domain.grade.dto.WinningBiddingStatisticsGetResponse;
import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreGradeRepositoryCustomImpl implements CoreGradeRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<GradeStatisticsGetResponse> findGradeStatisticsByGradeNo(int gradeNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(GradeStatisticsGetResponse.class,
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
						grade.expenditureStatistics.sumSnackExpenditure,
						grade.expenditureStatistics.sumLearningExpenditure,
						grade.expenditureStatistics.sumCouponExpenditure,
						grade.expenditureStatistics.sumGameExpenditure,
						grade.expenditureStatistics.sumEtcExpenditure,
						grade.asset,
						grade.transferAlertPeriod,
						grade.transferPeriod,
						grade.biddingStatistics.countFourteenDaysAgo,
						grade.biddingStatistics.countThirteenDaysAgo,
						grade.biddingStatistics.countTwelveDaysAgo,
						grade.biddingStatistics.countElevenDaysAgo,
						grade.biddingStatistics.countTenDaysAgo,
						grade.biddingStatistics.countNineDaysAgo,
						grade.biddingStatistics.countEightDaysAgo,
						grade.biddingStatistics.countSevenDaysAgo,
						grade.biddingStatistics.countSixDaysAgo,
						grade.biddingStatistics.countFiveDaysAgo,
						grade.biddingStatistics.countFourDaysAgo,
						grade.biddingStatistics.countThreeDaysAgo,
						grade.biddingStatistics.countTwoDaysAgo,
						grade.biddingStatistics.countOneDaysAgo,
						grade.salaryRecommendation
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
	public List<ExpenditureStatisticsGetResponse> findAllBiddingDealTypeStatistics() {
		return queryFactory
			.select(Projections.constructor(ExpenditureStatisticsGetResponse.class,
					account.price.sum(),
					account.dealType,
					account.gradeNo
				)
			)
			.from(account)
			.where(
				account.accountType.eq(AccountType.EXPENDITURE),
				account.dealType.notIn(DealType.SAVING, DealType.REWARD, DealType.SALARY)
			)
			.groupBy(account.gradeNo, account.dealType)
			.fetch();
	}

	@Override
	public List<WinningBiddingStatisticsGetResponse> findAllWinningBiddingStatistics() {
		StringTemplate formattedDate = Expressions.stringTemplate(
			"DATE_FORMAT({0}, {1})",
			bidding.createdAt,
			ConstantImpl.create("%Y-%m-%d")
		);

		return queryFactory
			.select(Projections.constructor(WinningBiddingStatisticsGetResponse.class,
					formattedDate,
					bidding.no.count(),
					bidding.gradeNo
				)
			)
			.from(bidding)
			.where(bidding.biddingStatus.eq(BiddingStatus.WINNING_BID))
			.groupBy(bidding.gradeNo, formattedDate)
			.fetch();
	}

	@Override
	public List<GradeBiddingAvgPriceResponse> findAllGradeBiddingPrice() {
		return queryFactory
			.select(Projections.constructor(GradeBiddingAvgPriceResponse.class,
					grade,
					ExpressionUtils.as(
						JPAExpressions
							.select(bidding.price.avg())
							.from(bidding)
							.where(
								bidding.biddingStatus.eq(BiddingStatus.WINNING_BID),
								bidding.gradeNo.eq(grade.no),
								bidding.createdAt.between(LocalDateTime.now().minusWeeks(4),
									LocalDateTime.now().minusWeeks(2))
							)
						, "avgBeforeFourWeeks"
					),
					ExpressionUtils.as(
						JPAExpressions
							.select(bidding.price.avg())
							.from(bidding)
							.where(
								bidding.biddingStatus.eq(BiddingStatus.WINNING_BID),
								bidding.gradeNo.eq(grade.no),
								bidding.createdAt.between(LocalDateTime.now().minusWeeks(2), LocalDateTime.now())
							)
						, "avgBeforeTwoWeeks"
					)
				)
			)
			.from(grade)
			.fetch();
	}
}

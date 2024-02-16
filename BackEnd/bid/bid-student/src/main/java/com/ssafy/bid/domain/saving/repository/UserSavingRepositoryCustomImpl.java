package com.ssafy.bid.domain.saving.repository;

import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;
import static com.ssafy.bid.domain.user.QAccount.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.TaxRateResponse;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;
import com.ssafy.bid.domain.user.AccountType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSavingRepositoryCustomImpl implements UserSavingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<UserSavingListGetResponse> findAllByUserNoAndGradeNo(int userNo, int gradeNo) {
		return queryFactory
			.select(Projections.constructor(UserSavingListGetResponse.class,
					grade.asset,
					saving.no,
					saving.name,
					saving.depositPeriod,
					saving.depositCycle,
					saving.depositPrice,
					saving.interestRate,
					saving.terms,
					ExpressionUtils.as(
						JPAExpressions
							.select(userSaving.count())
							.from(userSaving)
							.where(userSaving.savingNo.eq(saving.no))
						, "countMySaving"
					)
				)
			)
			.from(saving)
			.innerJoin(grade).on(grade.no.eq(saving.gradeNo))
			.where(
				grade.no.eq(gradeNo),
				grade.deletedAt.isNull()
			)
			.fetch();
	}

	@Override
	public Optional<TaxRateResponse> findAllBiddingIncomes(int gradeNo, int userNo) {
		return Optional.ofNullable(
			queryFactory
				.select(Projections.constructor(TaxRateResponse.class,
						grade.salary,
						ExpressionUtils.as(
							JPAExpressions
								.select(account.price.avg())
								.from(account)
								.where(
									account.gradeNo.eq(grade.no),
									account.accountType.eq(AccountType.INCOME),
									account.createdAt.between(LocalDateTime.now().minusDays(7), LocalDateTime.now())
								)
							, "avgGradeIncome"
						),
						ExpressionUtils.as(
							JPAExpressions
								.select(account.price.sum())
								.from(account)
								.where(
									account.userNo.eq(userNo),
									account.accountType.eq(AccountType.INCOME),
									account.createdAt.between(LocalDateTime.now().minusDays(7), LocalDateTime.now())
								)
							, "sumMyIncome"
						)
					)
				)
				.from(grade)
				.where(grade.no.eq(gradeNo))
				.fetchOne()
		);
	}
}

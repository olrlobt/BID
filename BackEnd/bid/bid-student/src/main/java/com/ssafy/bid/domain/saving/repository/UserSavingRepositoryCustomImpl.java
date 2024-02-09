package com.ssafy.bid.domain.saving.repository;

import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;

import java.util.List;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;

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
}

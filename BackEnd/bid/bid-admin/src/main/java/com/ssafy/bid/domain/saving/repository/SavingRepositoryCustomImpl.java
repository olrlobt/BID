package com.ssafy.bid.domain.saving.repository;

import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.saving.QSaving.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SavingRepositoryCustomImpl implements SavingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<SavingsResponse> findSavings(int gradeNo) {
		return queryFactory
			.select(Projections.constructor(SavingsResponse.class,
					grade.asset,
					saving.no,
					saving.name,
					saving.depositPeriod,
					saving.depositCycle,
					saving.depositPrice,
					saving.interestRate,
					saving.terms
				)
			)
			.from(saving)
			.innerJoin(grade).on(grade.no.eq(saving.gradeNo))
			.where(
				saving.gradeNo.eq(gradeNo)
			)
			.fetch();
	}
}

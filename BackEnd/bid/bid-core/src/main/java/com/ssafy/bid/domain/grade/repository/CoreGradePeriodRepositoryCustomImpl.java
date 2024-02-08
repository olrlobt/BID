package com.ssafy.bid.domain.grade.repository;

import static com.ssafy.bid.domain.gradeperiod.QGradePeriod.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.grade.dto.GradePeriodsGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreGradePeriodRepositoryCustomImpl implements CoreGradePeriodRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<GradePeriodsGetResponse> findAllGradePeriodByGradeNo(int gradeNo) {
		return queryFactory
			.select(Projections.constructor(GradePeriodsGetResponse.class,
					gradePeriod.no,
					gradePeriod.sequence,
					gradePeriod.startPeriod,
					gradePeriod.endPeriod
				)
			)
			.from(gradePeriod)
			.where(gradePeriod.gradeNo.eq(gradeNo))
			.fetch();
	}
}

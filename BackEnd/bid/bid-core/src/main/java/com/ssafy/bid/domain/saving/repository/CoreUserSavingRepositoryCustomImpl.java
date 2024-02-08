package com.ssafy.bid.domain.saving.repository;

import static com.ssafy.bid.domain.saving.QUserSaving.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreUserSavingRepositoryCustomImpl implements CoreUserSavingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<SavingExpireRequest> findAllSavingExpireInfos() {
		return queryFactory
			.select(Projections.constructor(SavingExpireRequest.class,
					userSaving.no,
					userSaving.endPeriod,
					userSaving.currentPrice,
					student
				)
			)
			.from(userSaving)
			.innerJoin(student).on(student.no.eq(userSaving.userNo))
			.fetch();
	}
}

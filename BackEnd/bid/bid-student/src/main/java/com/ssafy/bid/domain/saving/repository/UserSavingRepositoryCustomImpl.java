package com.ssafy.bid.domain.saving.repository;

import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSavingRepositoryCustomImpl implements UserSavingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<SavingTransferAlertRequest> findSavingTransferInfos() {
		return queryFactory
			.select(Projections.constructor(SavingTransferAlertRequest.class,
					userSaving.userNo,
					saving.depositPrice,
					grade.transferPeriod
				)
			)
			.from(userSaving)
			.innerJoin(saving).on(saving.no.eq(userSaving.savingNo))
			.innerJoin(grade).on(grade.no.eq(saving.gradeNo))
			.where(grade.deletedAt.isNull())
			.fetch();
	}
}

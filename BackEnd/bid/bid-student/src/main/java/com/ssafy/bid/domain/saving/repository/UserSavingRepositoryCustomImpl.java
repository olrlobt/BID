package com.ssafy.bid.domain.saving.repository;

import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.saving.QSaving.*;
import static com.ssafy.bid.domain.saving.QUserSaving.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserSavingRepositoryCustomImpl implements UserSavingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<SavingTransferAlertRequest> findAllSavingTransferInfos() {
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

	@Override
	public List<SavingExpireRequest> findAllSavingExpireInfos() {
		return queryFactory
			.select(Projections.constructor(SavingExpireRequest.class,
					userSaving.no,
					userSaving.endPeriod,
					userSaving.resultPrice,
					student
				)
			)
			.from(userSaving)
			.innerJoin(student).on(student.no.eq(userSaving.userNo))
			.fetch();
	}
}

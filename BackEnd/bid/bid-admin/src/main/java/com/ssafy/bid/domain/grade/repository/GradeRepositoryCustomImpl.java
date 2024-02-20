package com.ssafy.bid.domain.grade.repository;

import static com.querydsl.jpa.JPAExpressions.*;
import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.user.QAdmin.*;
import static com.ssafy.bid.domain.user.QSchool.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradeListGetResponse;
import com.ssafy.bid.domain.user.Admin;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GradeRepositoryCustomImpl implements GradeRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<GradeListGetResponse> findAllWithSchoolName(int userNo) {
		return queryFactory
			.select(Projections.constructor(GradeListGetResponse.class,
					grade.no,
					school.name,
					grade.year,
					grade.classRoom,
					grade.createdAt,
					ExpressionUtils.as(
						JPAExpressions
							.select(admin.mainGradeNo)
							.from(admin)
							.where(admin.no.eq(userNo))
						, "mainGradeNo"
					)
				)
			)
			.from(grade)
			.innerJoin(school).on(school.code.eq(grade.schoolCode))
			.where(grade.userNo.eq(userNo))
			.fetch();
	}

	@Override
	public boolean existsByGradeNo(int gradeNo) {
		Integer fetchOne = queryFactory
			.selectOne()
			.from(grade)
			.where(grade.no.eq(gradeNo))
			.fetchFirst();

		return fetchOne != null;
	}

	@Override
	public Optional<Admin> findAdminByUserNo(int userNo) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(admin)
				.where(admin.no.eq(userNo))
				.fetchOne()
		);
	}

	@Override
	public List<Grade> findAllByUserNo(int userNo) {
		return queryFactory
			.selectFrom(grade)
			.where(
				grade.userNo.eq(userNo),
				grade.deletedAt.isNull())
			.fetch();
	}
}

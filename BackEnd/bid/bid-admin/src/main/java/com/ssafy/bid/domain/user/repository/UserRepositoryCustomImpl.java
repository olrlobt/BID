package com.ssafy.bid.domain.user.repository;

import static com.ssafy.bid.domain.grade.QGrade.*;
import static com.ssafy.bid.domain.user.QAdmin.*;
import static com.ssafy.bid.domain.user.QSchool.*;
import static com.ssafy.bid.domain.user.QStudent.*;
import static com.ssafy.bid.domain.user.QUser.*;

import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<StudentsFindResponse> findAllStudentByGradeNo(int gradeNo) {
		return queryFactory.select(Projections.constructor(StudentsFindResponse.class,
					student.no,
					student.id,
					student.name,
					student.asset
				)
			)
			.from(student)
			.where(
				student.gradeNo.eq(gradeNo),
				student.deletedAt.isNull()
			)
			.fetch();
	}

	@Override
	public List<SchoolResponse> findByNameContaining(String name) {
		return queryFactory
			.select(Projections.constructor(SchoolResponse.class,
					school.no,
					school.name,
					school.region,
					school.code
				)
			)
			.from(school)
			.where(nameCondition(name))
			.fetch();
	}

	private BooleanExpression nameCondition(String name) {
		if (name == null) {
			return null;
		}
		return school.name.like("%" + name + "%");
	}

	@Override
	public boolean checkExistsById(String id) {
		return Boolean.TRUE.equals(queryFactory
			.select(
				new CaseBuilder()
					.when(user.no.count().goe(1)).then(true)
					.otherwise(false)
			)
			.from(user)
			.groupBy(user.id)
			.having(user.id.eq(id))
			.fetchOne());

	}

	@Override
	public List<Student> findAllByIds(List<Integer> userNos) {
		return queryFactory
			.selectFrom(student)
			.where(student.no.in(userNos))
			.fetch();
	}

	@Override
	public List<BallsFindResponse> findAllBallsByGradeNo(int gradeNo) {
		return queryFactory
			.select(Projections.constructor(BallsFindResponse.class,
					student.no,
					student.name,
					student.ballCount
				)
			)
			.from(student)
			.innerJoin(grade).on(grade.no.eq(student.gradeNo))
			.where(
				student.gradeNo.eq(gradeNo),
				student.deletedAt.isNull(),
				grade.deletedAt.isNull()
			)
			.orderBy(student.no.asc())
			.fetch();
	}

	@Override
	public List<Student> findAllStudentsByGradeNo(int gradeNo) {
		return queryFactory
			.selectFrom(student)
			.innerJoin(grade).on(grade.no.eq(student.gradeNo))
			.where(
				student.gradeNo.eq(gradeNo),
				student.deletedAt.isNull(),
				grade.deletedAt.isNull()
			)
			.fetch();
	}

	@Override
	public Optional<String> findIdByNameAndTel(String name, String tel) {
		return Optional.ofNullable(
			queryFactory
				.select(admin.id)
				.from(admin)
				.where(admin.name.eq(name), admin.tel.eq(tel))
				.fetchOne()
		);
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
}

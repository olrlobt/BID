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
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentsGetResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public boolean existsById(String id) {
		Integer fetchOne = queryFactory
			.selectOne()
			.from(user)
			.where(
				user.deletedAt.isNull(),
				user.id.eq(id)
			)
			.fetchFirst();

		return fetchOne != null;
	}

	@Override
	public Optional<Student> findStudentByUserNo(int userNo) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(student)
				.where(student.no.eq(userNo))
				.fetchOne()
		);
	}

	@Override
	public List<SchoolsFindResponse> findSchoolsByName(String name) {
		return queryFactory
			.select(Projections.constructor(SchoolsFindResponse.class,
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

	@Override
	public Optional<Admin> findAdminById(String id) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(admin)
				.where(admin.id.eq(id))
				.fetchOne()
		);
	}

	@Override
	public boolean existsByIdAndTel(String id, String tel) {
		Integer fetchOne = queryFactory
			.selectOne()
			.from(admin)
			.where(
				admin.id.eq(id),
				admin.tel.eq(tel)
			)
			.fetchFirst();

		return fetchOne != null;
	}

	private BooleanExpression nameCondition(String name) {
		if (name == null) {
			return null;
		}
		return school.name.like("%" + name + "%");
	}

	@Override
	public List<StudentsGetResponse> findAllStudentByGradeNo(int gradeNo) {
		return queryFactory.select(Projections.constructor(StudentsGetResponse.class,
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
	public Optional<String> findUserIdByNameAndTel(String name, String tel) {
		return Optional.ofNullable(
			queryFactory
				.select(admin.id)
				.from(admin)
				.where(
					admin.name.eq(name),
					admin.tel.eq(tel),
					admin.deletedAt.isNull()
				)
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
}

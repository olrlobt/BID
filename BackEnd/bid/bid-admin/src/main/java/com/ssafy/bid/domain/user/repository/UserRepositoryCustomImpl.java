package com.ssafy.bid.domain.user.repository;

import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.user.dto.StudentsResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<StudentsResponse> findStudents(int gradeNo) {
		return queryFactory.select(Projections.constructor(StudentsResponse.class,
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
}

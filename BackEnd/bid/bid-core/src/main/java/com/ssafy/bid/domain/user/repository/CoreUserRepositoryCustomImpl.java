package com.ssafy.bid.domain.user.repository;

import static com.ssafy.bid.domain.user.QUser.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.user.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreUserRepositoryCustomImpl implements CoreUserRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<User> findById(String id) {
		return Optional.ofNullable(
			queryFactory
				.selectFrom(user)
				.where(user.id.eq(id))
				.fetchOne()
		);
	}
}

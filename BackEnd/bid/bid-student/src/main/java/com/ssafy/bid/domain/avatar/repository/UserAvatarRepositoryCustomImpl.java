package com.ssafy.bid.domain.avatar.repository;

import static com.ssafy.bid.domain.avatar.QAvatar.*;
import static com.ssafy.bid.domain.avatar.QUserAvatar.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsFindResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAvatarRepositoryCustomImpl implements UserAvatarRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<UserAvatarsFindResponse> findUserAvatars(int userNo) {
		return queryFactory
			.select(Projections.constructor(UserAvatarsFindResponse.class,
					userAvatar.no,
					avatar.url,
					avatar.no
				)
			)
			.from(userAvatar)
			.innerJoin(avatar).on(avatar.no.eq(userAvatar.avatarNo))
			.where(userAvatar.userNo.eq(userNo))
			.fetch();
	}
}

package com.ssafy.bid.domain.reward.repository;

import static com.ssafy.bid.domain.reward.QReward.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.reward.dto.RewardsFindResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RewardRepositoryCustomImpl implements RewardRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<RewardsFindResponse> findRewards(int gradeNo) {
		return queryFactory
			.select(Projections.constructor(RewardsFindResponse.class,
					reward.no,
					reward.name,
					reward.price
				)
			)
			.from(reward)
			.where(
				reward.gradeNo.eq(gradeNo)
			)
			.fetch();
	}
}

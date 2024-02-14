package com.ssafy.bid.domain.reward.repository;

import java.util.List;

import com.ssafy.bid.domain.reward.dto.RewardListGetResponse;

public interface RewardRepositoryCustom {
	List<RewardListGetResponse> findAllByGradeNo(int gradeNo);

	boolean existsByNo(int rewardNo);
}

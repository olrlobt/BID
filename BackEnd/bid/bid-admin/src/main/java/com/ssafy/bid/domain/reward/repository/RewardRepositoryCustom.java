package com.ssafy.bid.domain.reward.repository;

import java.util.List;

import com.ssafy.bid.domain.reward.dto.RewardsFindResponse;

public interface RewardRepositoryCustom {
	List<RewardsFindResponse> findRewards(int gradeNo);
}

package com.ssafy.bid.domain.reward.service;

import java.util.List;

import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardsFindResponse;

public interface RewardService {
	void saveReward(int gradeNo, RewardSaveRequest rewardSaveRequest);

	List<RewardsFindResponse> findRewards(int gradeNo);
}

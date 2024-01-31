package com.ssafy.bid.domain.reward.service;

import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;

public interface RewardService {
	void saveReward(int gradeNo, RewardSaveRequest rewardSaveRequest);
}

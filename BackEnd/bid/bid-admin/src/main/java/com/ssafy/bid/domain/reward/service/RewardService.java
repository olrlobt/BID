package com.ssafy.bid.domain.reward.service;

import java.util.List;

import com.ssafy.bid.domain.reward.dto.RewardListGetResponse;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardSendRequest;

public interface RewardService {
	void saveReward(int gradeNo, RewardSaveRequest rewardSaveRequest);

	List<RewardListGetResponse> getRewards(int gradeNo);

	void deleteReward(int rewardNo);

	void sendReward(RewardSendRequest rewardSendRequest);
}

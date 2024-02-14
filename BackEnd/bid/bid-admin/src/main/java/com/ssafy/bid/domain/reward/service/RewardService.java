package com.ssafy.bid.domain.reward.service;

import java.util.List;

import com.ssafy.bid.domain.reward.dto.RewardListGetResponse;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardSendRequest;
import com.ssafy.bid.domain.user.UserType;

public interface RewardService {
	void saveReward(UserType userType, int gradeNo, RewardSaveRequest rewardSaveRequest);

	List<RewardListGetResponse> getRewards(UserType userType, int gradeNo);

	void deleteReward(UserType userType, int rewardNo);

	void sendReward(UserType userType, RewardSendRequest rewardSendRequest);
}

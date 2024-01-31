package com.ssafy.bid.domain.reward.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.reward.Reward;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.repository.RewardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RewardServiceImpl implements RewardService {

	private final RewardRepository rewardRepository;

	@Override
	public void saveReward(int gradeNo, RewardSaveRequest rewardSaveRequest) {
		Reward reward = rewardSaveRequest.toEntity(gradeNo);
		rewardRepository.save(reward);
	}
}

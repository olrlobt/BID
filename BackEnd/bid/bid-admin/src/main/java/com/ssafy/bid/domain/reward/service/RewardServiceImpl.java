package com.ssafy.bid.domain.reward.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.reward.Reward;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardsFindResponse;
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

	@Override
	@Transactional(readOnly = true)
	public List<RewardsFindResponse> findRewards(int gradeNo) {
		return rewardRepository.findRewards(gradeNo);
	}

	@Override
	public void deleteReward(int rewardNo) {
		rewardRepository.deleteById(rewardNo);
	}
}

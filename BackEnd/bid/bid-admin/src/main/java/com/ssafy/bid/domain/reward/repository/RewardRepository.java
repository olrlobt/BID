package com.ssafy.bid.domain.reward.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.reward.Reward;

public interface RewardRepository extends JpaRepository<Reward, Integer> {
}

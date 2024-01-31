package com.ssafy.bid.domain.reward.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.service.RewardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RewardApi {

	private final RewardService rewardService;

	@PostMapping("/{gradeNo}/rewards")
	public ResponseEntity<Void> saveReward(@PathVariable int gradeNo, RewardSaveRequest rewardSaveRequest) {
		rewardService.saveReward(gradeNo, rewardSaveRequest);
		return ResponseEntity.status(CREATED).build();
	}
}

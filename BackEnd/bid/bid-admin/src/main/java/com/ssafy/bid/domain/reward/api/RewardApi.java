package com.ssafy.bid.domain.reward.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardSendRequest;
import com.ssafy.bid.domain.reward.dto.RewardsFindResponse;
import com.ssafy.bid.domain.reward.service.RewardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class RewardApi {

	private final RewardService rewardService;

	@PostMapping("/{gradeNo}/rewards")
	public ResponseEntity<Void> saveReward(@PathVariable int gradeNo, RewardSaveRequest rewardSaveRequest) {
		rewardService.saveReward(gradeNo, rewardSaveRequest);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/{gradeNo}/rewards")
	public List<RewardsFindResponse> findRewards(@PathVariable int gradeNo) {
		return rewardService.findRewards(gradeNo);
	}

	@DeleteMapping("/rewards/{rewardsNo}")
	public ResponseEntity<Void> deleteReward(@PathVariable int rewardsNo) {
		rewardService.deleteReward(rewardsNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PostMapping("/rewards/send")
	public void sendReward(RewardSendRequest rewardSendRequest) {
		rewardService.sendReward(rewardSendRequest);
	}
}

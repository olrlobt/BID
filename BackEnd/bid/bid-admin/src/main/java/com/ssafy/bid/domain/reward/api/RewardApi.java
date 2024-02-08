package com.ssafy.bid.domain.reward.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.reward.dto.RewardListGetResponse;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardSendRequest;
import com.ssafy.bid.domain.reward.service.RewardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class RewardApi {

	private final RewardService rewardService;

	@PostMapping("/{gradeNo}/rewards")
	public ResponseEntity<?> saveReward(@PathVariable int gradeNo,
		@RequestBody RewardSaveRequest rewardSaveRequest) {
		rewardService.saveReward(gradeNo, rewardSaveRequest);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/{gradeNo}/rewards")
	public ResponseEntity<List<RewardListGetResponse>> getRewards(@PathVariable int gradeNo) {
		List<RewardListGetResponse> responses = rewardService.getRewards(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@DeleteMapping("/{gradeNo}/rewards/{rewardsNo}")
	public ResponseEntity<?> deleteReward(@PathVariable int rewardsNo) {
		rewardService.deleteReward(rewardsNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PostMapping("/{gradeNo}/rewards/send")
	public ResponseEntity<?> sendReward(@RequestBody RewardSendRequest rewardSendRequest) {
		rewardService.sendReward(rewardSendRequest);
		return ResponseEntity.status(OK).build();
	}
}

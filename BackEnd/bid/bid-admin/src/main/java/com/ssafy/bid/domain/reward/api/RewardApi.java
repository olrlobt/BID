package com.ssafy.bid.domain.reward.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class RewardApi {

	private final RewardService rewardService;

	@PostMapping("/{gradeNo}/rewards")
	public ResponseEntity<?> saveReward(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo,
		@RequestBody RewardSaveRequest rewardSaveRequest
	) {
		UserType userType = userDetails.getUserInfo().getUserType();
		rewardService.saveReward(userType, gradeNo, rewardSaveRequest);
		return ResponseEntity.status(CREATED).build();
	}

	@GetMapping("/{gradeNo}/rewards")
	public ResponseEntity<List<RewardListGetResponse>> getRewards(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo
	) {
		UserType userType = userDetails.getUserInfo().getUserType();
		List<RewardListGetResponse> responses = rewardService.getRewards(userType, gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@DeleteMapping("/{gradeNo}/rewards/{rewardsNo}")
	public ResponseEntity<?> deleteReward(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int rewardsNo
	) {
		UserType userType = userDetails.getUserInfo().getUserType();
		rewardService.deleteReward(userType, rewardsNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}

	@PostMapping("/{gradeNo}/rewards/send")
	public ResponseEntity<?> sendReward(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody RewardSendRequest rewardSendRequest
	) {
		UserType userType = customUserDetails.getUserInfo().getUserType();
		rewardService.sendReward(userType, rewardSendRequest);
		return ResponseEntity.status(OK).build();
	}
}

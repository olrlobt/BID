package com.ssafy.bid.domain.saving.api;

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

import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;
import com.ssafy.bid.domain.saving.service.SavingService;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SavingApi {

	private final SavingService savingService;

	@GetMapping("/savings")
	public ResponseEntity<List<UserSavingListGetResponse>> findSavings(
		@AuthenticationPrincipal CustomUserDetails userDetails
	) {
		CustomUserInfo userInfo = userDetails.getUserInfo();
		List<UserSavingListGetResponse> responses = savingService.getAllSavings(userInfo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PostMapping("/savings")
	public ResponseEntity<?> saveSaving(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody SavingSaveRequest savingSaveRequest
	) {
		CustomUserInfo userInfo = userDetails.getUserInfo();
		savingService.saveSavings(userInfo, savingSaveRequest);
		return ResponseEntity.status(CREATED).build();
	}

	@DeleteMapping("/savings/{savingNo}")
	public ResponseEntity<?> deleteSaving(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int savingNo
	) {
		CustomUserInfo userInfo = userDetails.getUserInfo();
		savingService.deleteSavings(userInfo, savingNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}
}

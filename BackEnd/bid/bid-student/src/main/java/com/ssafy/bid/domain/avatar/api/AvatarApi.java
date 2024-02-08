package com.ssafy.bid.domain.avatar.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.avatar.dto.UserAvatarUpdateRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsGetResponse;
import com.ssafy.bid.domain.avatar.service.AvatarService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class AvatarApi {

	private final AvatarService avatarService;

	@GetMapping("/{userNo}/avatars")
	public ResponseEntity<List<UserAvatarsGetResponse>> getUserAvatars(@PathVariable int userNo) {
		List<UserAvatarsGetResponse> responses = avatarService.getUserAvatars(userNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PatchMapping("/avatars")
	public ResponseEntity<?> updateAvatar(
		// @AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody UserAvatarUpdateRequest userAvatarUpdateRequest
	) {
		// int userNo = customUserDetails.getUserInfo().getNo();
		avatarService.updateUserAvatar(2, userAvatarUpdateRequest);
		return ResponseEntity.status(OK).build();
	}
}

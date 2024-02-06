package com.ssafy.bid.domain.avatar.api;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.avatar.dto.UserAvatarModifyRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsFindResponse;
import com.ssafy.bid.domain.avatar.service.AvatarService;
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class AvatarApi {

	private final AvatarService avatarService;

	@GetMapping("/{userNo}/avatars")
	public List<UserAvatarsFindResponse> findUserAvatars(@PathVariable int userNo) {
		return avatarService.findUserAvatars(userNo);
	}

	@PatchMapping("/avatars")
	public void modifyUserAvatar(
		@AuthenticationPrincipal CustomUserDetails customUserDetails,
		@RequestBody UserAvatarModifyRequest userAvatarModifyRequest
	) {
		int userNo = customUserDetails.getUserInfo().getNo();
		avatarService.modifyAvatar(userNo, userAvatarModifyRequest);
	}
}

package com.ssafy.bid.domain.avatar.service;

import java.util.List;

import com.ssafy.bid.domain.avatar.dto.UserAvatarUpdateRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsGetResponse;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

public interface AvatarService {
	List<UserAvatarsGetResponse> getUserAvatars(UserType userType, int userNo);

	void updateUserAvatar(CustomUserInfo userInfo, UserAvatarUpdateRequest userAvatarUpdateRequest);
}

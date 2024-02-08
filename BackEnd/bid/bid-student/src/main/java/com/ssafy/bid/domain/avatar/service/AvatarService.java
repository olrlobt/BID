package com.ssafy.bid.domain.avatar.service;

import java.util.List;

import com.ssafy.bid.domain.avatar.dto.UserAvatarUpdateRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsGetResponse;

public interface AvatarService {
	List<UserAvatarsGetResponse> getUserAvatars(int userNo);

	void updateUserAvatar(int userNo, UserAvatarUpdateRequest userAvatarUpdateRequest);
}

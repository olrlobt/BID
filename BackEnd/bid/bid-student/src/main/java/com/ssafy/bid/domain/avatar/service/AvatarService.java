package com.ssafy.bid.domain.avatar.service;

import java.util.List;

import com.ssafy.bid.domain.avatar.dto.UserAvatarsFindResponse;

public interface AvatarService {
	List<UserAvatarsFindResponse> findUserAvatars(int userNo);
}

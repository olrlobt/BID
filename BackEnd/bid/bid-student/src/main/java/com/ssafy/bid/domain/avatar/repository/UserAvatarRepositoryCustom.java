package com.ssafy.bid.domain.avatar.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.avatar.dto.UserAvatarsFindResponse;

public interface UserAvatarRepositoryCustom {
	List<UserAvatarsFindResponse> findUserAvatars(int userNo);

	Optional<String> findUrlByUserAvatarNo(int userAvatarNo);
}

package com.ssafy.bid.domain.avatar.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.avatar.dto.UserAvatarsGetResponse;

public interface UserAvatarRepositoryCustom {
	List<UserAvatarsGetResponse> findAllByUserNo(int userNo);

	Optional<String> findUrlByUserAvatarNo(int userAvatarNo);
}

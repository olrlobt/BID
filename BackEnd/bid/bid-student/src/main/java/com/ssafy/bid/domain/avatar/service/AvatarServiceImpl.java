package com.ssafy.bid.domain.avatar.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.avatar.dto.UserAvatarsFindResponse;
import com.ssafy.bid.domain.avatar.repository.UserAvatarRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AvatarServiceImpl implements AvatarService {

	private final UserAvatarRepository userAvatarRepository;

	@Override
	public List<UserAvatarsFindResponse> findUserAvatars(int userNo) {
		return userAvatarRepository.findUserAvatars(userNo);
	}
}

package com.ssafy.bid.domain.avatar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.avatar.dto.UserAvatarUpdateRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsGetResponse;
import com.ssafy.bid.domain.avatar.repository.UserAvatarRepository;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AvatarServiceImpl implements AvatarService {

	private final UserAvatarRepository userAvatarRepository;
	private final UserRepository userRepository;

	@Override
	public List<UserAvatarsGetResponse> getUserAvatars(int userNo) {
		return userAvatarRepository.findAllByUserNo(userNo);
	}

	@Override
	@Transactional
	public void updateUserAvatar(int userNo, UserAvatarUpdateRequest userAvatarUpdateRequest) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("아바타를 수정하려는 User 가 없음.", userNo));

		if (!(user instanceof Student student)) {
			throw new InvalidParameterException("아바타를 수정하려는 User 의 타입이 올바르지 않음.", userNo);
		}

		String url = userAvatarRepository.findUrlByUserAvatarNo(userAvatarUpdateRequest.getNo())
			.orElseThrow(
				() -> new ResourceNotFoundException("수정하려는 UserAvatar 가 없음.", userAvatarUpdateRequest.getNo()));
		student.updateAvatar(url);
	}
}

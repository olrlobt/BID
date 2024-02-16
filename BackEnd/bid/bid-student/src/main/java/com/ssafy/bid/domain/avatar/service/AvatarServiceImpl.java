package com.ssafy.bid.domain.avatar.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.avatar.Avatar;
import com.ssafy.bid.domain.avatar.UserAvatar;
import com.ssafy.bid.domain.avatar.dto.AvatarRequest;
import com.ssafy.bid.domain.avatar.dto.AvatarResponse;
import com.ssafy.bid.domain.avatar.dto.UserAvatarUpdateRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsGetResponse;
import com.ssafy.bid.domain.avatar.repository.AvatarRepository;
import com.ssafy.bid.domain.avatar.repository.UserAvatarRepository;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AvatarServiceImpl implements AvatarService {

	private final UserAvatarRepository userAvatarRepository;
	private final UserRepository userRepository;
	private final AvatarRepository avatarRepository;

	@Override
	public List<UserAvatarsGetResponse> getUserAvatars(UserType userType, int userNo) {
		if (!userType.equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("아바타목록조회: Student 권한 사용자가 아님.");
		}

		return userAvatarRepository.findAllByUserNo(userNo);
	}

	@Override
	@Transactional
	public void updateUserAvatar(CustomUserInfo userInfo, UserAvatarUpdateRequest userAvatarUpdateRequest) {
		if (!userInfo.getUserType().equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("아바타수정: Student 권한 사용자가 아님.");
		}

		System.out.println("아바타 변경 -------------");
		System.out.println(userInfo.getNo());

		Student student = userRepository.findStudentByUserNo(userInfo.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("아바타수정: Student 가 없음.", userInfo.getNo()));

		String url = userAvatarRepository.findUrlByUserAvatarNo(userAvatarUpdateRequest.getNo())
			.orElseThrow(
				() -> new ResourceNotFoundException("아바타수정: UserAvatar 가 없음.", userAvatarUpdateRequest.getNo()));

		System.out.println(url);
		System.out.println(student.getProfileImgUrl());
		student.updateAvatar(url);
		System.out.println(student.getProfileImgUrl());
		System.out.println("아바타 변경 -------------");
	}

	@Override
	@Transactional
	public AvatarResponse buyAvatar(int userNo, AvatarRequest avatarRequest) {

		Student student = userRepository.findStudentByUserNo(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("아바타구매: Student 가 없음.", userNo));

		List<Avatar> allAvatars = avatarRepository.findAll();
		Avatar avatar = allAvatars.get(ThreadLocalRandom.current().nextInt(allAvatars.size()));

		userAvatarRepository.save(UserAvatar.builder()
			.avatarNo(avatar.getNo())
			.userNo(userNo)
			.build());

		student.subtractPrice(avatar.getPrice());
		return avatar.toDto();
	}
}

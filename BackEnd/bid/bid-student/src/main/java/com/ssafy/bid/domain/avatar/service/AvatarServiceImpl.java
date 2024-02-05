package com.ssafy.bid.domain.avatar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.avatar.dto.UserAvatarModifyRequest;
import com.ssafy.bid.domain.avatar.dto.UserAvatarsFindResponse;
import com.ssafy.bid.domain.avatar.repository.UserAvatarRepository;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AvatarServiceImpl implements AvatarService {

	private final UserAvatarRepository userAvatarRepository;
	private final UserRepository userRepository;

	@Override
	public List<UserAvatarsFindResponse> findUserAvatars(int userNo) {
		return userAvatarRepository.findUserAvatars(userNo);
	}

	@Override
	@Transactional
	public void modifyAvatar(int userNo, UserAvatarModifyRequest userAvatarModifyRequest) {
		Student student = (Student)userRepository.findById(userNo)
			.orElseThrow(() -> new IllegalArgumentException(""));//TODO: 커스텀 예외

		String url = userAvatarRepository.findUrlByUserAvatarNo(userAvatarModifyRequest.getNo())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저아바타"));//TODO: 커스텀 예외처리

		student.modifyProfileImgUrl(url);
	}
}

package com.ssafy.bid.domain.user.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.TokenBlacklist;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.dto.LoginRequest;
import com.ssafy.bid.domain.user.repository.CoreTokenBlacklistRepository;
import com.ssafy.bid.domain.user.repository.CoreUserRepository;
import com.ssafy.bid.domain.user.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CoreUserServiceImpl implements CoreUserService {

	private final JwtTokenProvider jwtTokenProvider;
	private final CoreUserRepository coreUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final CoreTokenBlacklistRepository coreTokenBlacklistRepository;

	@Override
	public String login(LoginRequest loginRequest) {
		String id = loginRequest.getId();
		String password = loginRequest.getPassword();

		User user = coreUserRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 유저 없음"));//TODO: 커스텀 예외처리

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호 불일치"); // TODO: 커스텀 예외처리
		}

		CustomUserInfo userInfo = CustomUserInfo.builder()
			.no(user.getNo())
			.id(user.getId())
			.password(user.getPassword())
			.name(user.getName())
			.schoolNo(user.getSchoolNo())
			.gradeNo(user instanceof Student ? ((Student)user).getGradeNo() : -1)
			.tel(user instanceof Admin ? ((Admin)user).getTel() : null)
			.build();

		return jwtTokenProvider.createAccessToken(userInfo);
	}

	@Override
	@Transactional
	public void logout(String token) {
		String actualToken = token.startsWith("Bearer ") ? token.substring(7) : token;

		TokenBlacklist blacklist = TokenBlacklist.builder()
			.token(actualToken)
			.expiryDate(LocalDateTime.now().plusMinutes(30))
			.build();

		coreTokenBlacklistRepository.save(blacklist);
	}
}

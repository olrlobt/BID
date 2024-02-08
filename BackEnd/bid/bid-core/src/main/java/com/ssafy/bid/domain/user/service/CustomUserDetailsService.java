package com.ssafy.bid.domain.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.CoreUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final CoreUserRepository coreUserRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = coreUserRepository.findById(username)
			.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저 없음"));// TODO: 커스텀 예외처리

		CustomUserInfo customUserInfo = CustomUserInfo.builder()
			.no(user.getNo())
			.id(user.getId())
			.password(user.getPassword())
			.name(user.getName())
			.schoolNo(user.getSchoolNo())
			.gradeNo(user instanceof Student ? ((Student)user).getGradeNo() : -1)
			.tel(user instanceof Admin ? ((Admin)user).getTel() : null)
			.build();

		return new CustomUserDetails(customUserInfo);
	}
}

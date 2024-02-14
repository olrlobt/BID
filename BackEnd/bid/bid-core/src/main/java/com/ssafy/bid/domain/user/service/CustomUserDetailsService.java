package com.ssafy.bid.domain.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.CoreUserRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final CoreUserRepository coreUserRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = coreUserRepository.findById(username)
			.orElseThrow(() -> new ResourceNotFoundException("인증/인가: 해당 User 엔티티 없음.", username));

		CustomUserInfo customUserInfo = CustomUserInfo.builder()
			.no(user.getNo())
			.id(user.getId())
			.password(user.getPassword())
			.name(user.getName())
			.schoolNo(user.getSchoolNo())
			.gradeNo(user instanceof Student student ? student.getGradeNo() : -1)
			.tel(user instanceof Admin admin ? admin.getTel() : null)
			.userType(user instanceof Student ? UserType.STUDENT : UserType.ADMIN)
			.build();

		return new CustomUserDetails(customUserInfo);
	}
}

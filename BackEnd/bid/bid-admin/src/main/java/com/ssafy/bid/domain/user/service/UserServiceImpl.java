package com.ssafy.bid.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public List<StudentsResponse> searchStudents(int gradeNo) {
		return userRepository.searchStudents(gradeNo);
	}
}

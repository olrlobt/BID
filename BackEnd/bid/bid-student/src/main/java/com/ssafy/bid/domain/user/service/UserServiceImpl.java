package com.ssafy.bid.domain.user.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	@Transactional
	public void checkAttendance(int userNo) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("출석체크: 출석을 등록한 Student 엔티티가 없음.", userNo));

		if (user instanceof Student student) {
			student.checkAttendance();
			return;
		}

		throw new InvalidParameterException("출석체크: 출석등록 대상 회원타입이 아님.", userNo);
	}

	@Override
	public boolean isAttendanceChecked(CustomUserInfo userInfo) {
		if (!userInfo.getUserType().equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("학생권한이 아님.");
		}

		Attendance attendance = userRepository.findAttendanceByUserNo(userInfo.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("해당 리소스 없음.", userInfo.getNo()));

		int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
		boolean result;
		switch (dayOfWeek) {
			case 1 -> result = attendance.isMonday();
			case 2 -> result = attendance.isTuesday();
			case 3 -> result = attendance.isWednesday();
			case 4 -> result = attendance.isThursday();
			case 5 -> result = attendance.isFriday();
			default -> throw new InvalidParameterException("출석 가능한 요일이 아님.");
		}

		return result;
	}
}

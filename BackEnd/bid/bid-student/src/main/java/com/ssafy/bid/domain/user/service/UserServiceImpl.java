package com.ssafy.bid.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.repository.StudentRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void checkAttendance(int userNo) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("출석을 등록한 User 엔티티가 없음.", userNo));

		if (user instanceof Student student) {
			student.checkAttendance();
			return;
		}

		throw new InvalidParameterException("출석등록 대상 회원타입이 아님.", userNo);
	}

	@Override
	public AttendanceResponse getStudentAttendance(Integer userNo) {
		Student student = studentRepository.findById(userNo)
			.orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));
		Attendance attendance = student.getAttendance();
		return new AttendanceResponse(
			attendance.isMonday(),
			attendance.isTuesday(),
			attendance.isWednesday(),
			attendance.isThursday(),
			attendance.isFriday()
		);
	}
}

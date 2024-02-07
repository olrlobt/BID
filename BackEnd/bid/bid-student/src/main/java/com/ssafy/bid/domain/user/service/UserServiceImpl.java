package com.ssafy.bid.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.dto.PasswordResetRequest;
import com.ssafy.bid.domain.user.repository.StudentRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void checkAttendance(int userNo) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("출석등록-회원PK", userNo));

		if (user instanceof Student) {
			((Student)user).checkAttendance();
			return;
		}

		throw new InvalidParameterException("출석등록-회원PK", userNo);
	}

	@Transactional
	public void resetPassword(String userId, PasswordResetRequest passwordResetRequest) {
		Student student = studentRepository.findStudentById(userId)
			.orElseThrow(() -> new IllegalArgumentException("학생 정보를 찾을 수 없습니다."));

		if (!passwordEncoder.matches(passwordResetRequest.getCurrentPassword(), student.getPassword())) {
			throw new IllegalArgumentException(("현재 비밀번호가 일치하지 않습니다."));
		}

		if (!passwordResetRequest.getNewPassword().equals(passwordResetRequest.getConfirmPassword())) {
			throw new IllegalArgumentException("비밀번호 확인 값이 일치하지 않습니다.");
		}

		String encodedNewPassword = passwordEncoder.encode(passwordResetRequest.getNewPassword());
		student.changePassword(encodedNewPassword);
		studentRepository.save(student);
	}

	@Transactional(readOnly = true)
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

package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.Attendance;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.AttendanceResponse;
import com.ssafy.bid.domain.user.dto.PasswordResetRequest;
import com.ssafy.bid.domain.user.repository.StudentRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AccountsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsResponse;
import com.ssafy.bid.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public StudentResponse findStudent(int userNo, StudentRequest studentRequest) {
		List<UserCouponsResponse> userCouponsResponses = userRepository.findUserCoupons(userNo);
		List<AccountsResponse> accountsResponses = userRepository.findAccounts(userNo, studentRequest);
		StudentResponse studentResponse = userRepository.findStudent(userNo)
				.orElseThrow(() -> new IllegalArgumentException("일치하는 회원정보가 없습니다."));//TODO: 글로벌 예외처리

		studentResponse.setCouponsResponses(userCouponsResponses);
		studentResponse.setAccountsResponses(accountsResponses);

		return studentResponse;
	}

	@Override
	public List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest) {
		return userRepository.findAccount(userNo, accountRequest);
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

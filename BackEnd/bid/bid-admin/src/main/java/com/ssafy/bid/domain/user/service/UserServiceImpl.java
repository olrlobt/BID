package com.ssafy.bid.domain.user.service;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AccountsResponse;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.RegisterRequest;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentRegistrationRequest;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendRequest;
import com.ssafy.bid.domain.user.dto.TelAuthenticationSendResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsResponse;
import com.ssafy.bid.domain.user.dto.UserUpdateRequest;
import com.ssafy.bid.domain.user.dto.UserWithdrawalRequest;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final MessageService messageService;

	@Override
	public TelAuthenticationSendResponse sendTelAuthentication(
		TelAuthenticationSendRequest telAuthenticationSendRequest) {
		String code = RandomStringUtils.randomNumeric(6);
		messageService.sendAuthenticationCode(telAuthenticationSendRequest.getTel(), code);

		return new TelAuthenticationSendResponse(code);
	}

	@Override
	public List<StudentsResponse> findStudents(int gradeNo) {
		return userRepository.findStudents(gradeNo);
	}

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

	@Override
	public List<SchoolResponse> searchSchools(String name) {
		return userRepository.findByNameContaining(name);
	}

	@Override
	@Transactional
	public void registerUser(RegisterRequest request) {
		Admin admin = request.toEntity(passwordEncoder);
		userRepository.save(admin);
	}

	@Override
	public boolean isIdDuplicate(String id) {
		return userRepository.checkExistsById(id);
	}

	@Override
	public List<BallsFindResponse> findAllBalls(int gradeNo) {
		// 학급PK를 통해 학생들의 보유 공 개수 목록 조회
		List<BallsFindResponse> responses = userRepository.findAllBallsByGradeNo(gradeNo);

		// 파라미터 검증
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("보유공개수조회-학급PK", gradeNo);
		}

		// 응답 반환
		return userRepository.findAllBallsByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void modifyBalls(int gradeNo) {
		// 학급PK를 통해 학생 목록 조회
		List<Student> responses = userRepository.findAllStudentsByGradeNo(gradeNo);

		// 파라미터 검증
		if (responses.isEmpty()) {
			throw new ResourceNotFoundException("학생목록조회-학급PK", gradeNo);
		}

		// 보유 공 개수 초기화
		responses.forEach(Student::resetBalls);
	}

	@Override
	public String findUserId(String name, String tel) {
		return userRepository.findIdByNameAndTel(name, tel)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 사용자 존재 X")); //TODO: 커스텀 예외처리
	}

	@Override
	@Transactional
	public void deleteUser(Integer userNo, UserWithdrawalRequest request) {
		User user = userRepository.findById(userNo)
			.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")); //TODO: 커스텀 예외처리

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다."); //TODO: 커스텀 예외처리
		}

		userRepository.delete(user);
	}

	@Override
	@Transactional
	public void updateUser(Integer userNo, UserUpdateRequest request) {
		Admin admin = userRepository.findAdminByUserNo(userNo)
			.orElseThrow(() -> new IllegalArgumentException("회원 존재 X"));//TODO: 커스텀 예외처리

		admin.update(request.getName(), request.getSchoolNo(), request.getTel());
	}

	@Override
	@Transactional
	public void registerStudent(StudentRegistrationRequest request) {
		userRepository.save(request.toEntity(passwordEncoder));
	}
}

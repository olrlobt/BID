package com.ssafy.bid.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AccountsResponse;
import com.ssafy.bid.domain.user.dto.BallsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsResponse;
import com.ssafy.bid.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public List<StudentsResponse> findStudents(int gradeNo) {
		return userRepository.findStudents(gradeNo);
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest) {
		return userRepository.findAccount(userNo, accountRequest);
	}

	@Override
	public List<School> searchSchools(String name) {
		return userRepository.findByNameContaining(name);
	}

	private Integer findSchoolNoByCode(String code) {
		return userRepository.findByCode(code)
				.map(School::getNo)
				.orElseThrow(() -> new IllegalArgumentException("해당 코드를 가진 학교가 없습니다."));
	}

	@Override
	@Transactional
	public void registerUser(RegisterRequest request) throws Exception {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
		}
		if (isIdDuplicate(request.getId())) {
			throw new Exception("이미 사용 중인 아이디입니다.");
		}

		Integer schoolNo = findSchoolNoByCode(request.getSchoolCode());
		String encryptedPassword = passwordEncoder.encode(request.getPassword());

		Admin admin = request.toEntity(schoolNo, encryptedPassword);
		userRepository.save(admin);
	}

	@Override
	public boolean isIdDuplicate(String id) {
		return userRepository.checkExistsById(id);
	}

	@Transactional(readOnly = true)
	public List<BallsResponse> findBalls(int gradeNo) {
		return userRepository.findBalls(gradeNo);
	}

	@Override
	public void modifyBalls(int gradeNo) {
		userRepository.resetBallCounts(gradeNo);
	}

	@Override
	public String findUserId(String name, String tel) throws Exception {
		return userRepository.findByNameAndTel(name, tel)
				.map(user -> user.getId())
				.orElseThrow(() -> new Exception("이름과 전화번호가 일치하지 않습니다."));
	}

	@Override
	public void deleteUser(Integer userNo, String password) throws Exception {
		User user = userRepository.findById(userNo)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
		userRepository.delete(user);
	}

	@Override
	@Transactional
	public void updateUser(Integer userNo, UserUpdateRequest request) throws Exception {
		User existingUser = userRepository.findById(userNo)
				.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
		Admin updatedAdmin = request.toEntity(existingUser);
		userRepository.save(updatedAdmin);
	}

	@Override
	@Transactional
	public void registerStudent(StudentRegistrationRequest request) {
		userRepository.save(request.toEntity(passwordEncoder));
	}
}

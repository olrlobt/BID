package com.ssafy.bid.domain.user.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.dto.*;
import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

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

		Admin admin = Admin.builder()
				.id(request.getId())
				.password(request.getPassword())
				.name(request.getName())
				.tel(request.getTel())
				.schoolNo(schoolNo)
				.build();
		userRepository.save(admin);
	}

	@Override
	public boolean isIdDuplicate(String id) {
		return userRepository.existsById(id);
	}

}
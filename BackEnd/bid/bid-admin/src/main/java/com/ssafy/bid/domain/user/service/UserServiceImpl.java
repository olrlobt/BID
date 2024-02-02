package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.*;
import com.ssafy.bid.domain.user.repository.UserRepository;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

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
	@Transactional(readOnly = true)
	public List<BallsResponse> findBalls(int gradeNo) {
		return userRepository.findBalls(gradeNo);
	}

	@Override
	public void modifyBalls(int gradeNo) {
		userRepository.resetBallCounts(gradeNo);
	}
}

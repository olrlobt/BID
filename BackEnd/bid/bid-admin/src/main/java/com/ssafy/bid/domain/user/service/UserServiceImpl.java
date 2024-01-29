package com.ssafy.bid.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

	@Override
	public StudentResponse studentSearch(int userNo, StudentRequest studentRequest) {
		List<UserCouponsResponse> userCouponsResponses = userRepository.searchUserCoupons(userNo);
		List<AccountsResponse> accountsResponses = userRepository.searchAccounts(userNo, studentRequest);
		StudentResponse studentResponse = userRepository.searchStudent(userNo)
			.orElseThrow(() -> new IllegalArgumentException("일치하는 회원정보가 없습니다."));//TODO: 글로벌 예외처리

		studentResponse.setCouponsResponses(userCouponsResponses);
		studentResponse.setAccountsResponses(accountsResponses);

		return studentResponse;
	}
}

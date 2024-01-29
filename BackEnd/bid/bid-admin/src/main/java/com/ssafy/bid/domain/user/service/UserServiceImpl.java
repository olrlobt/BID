package com.ssafy.bid.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;
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

		for (AccountsResponse accountsResponse : accountsResponses) {
			if (accountsResponse.getDealType().equals(DealType.SNACK)) {
				studentResponse.addSnackSum(accountsResponse.getTotalPrice());
			} else if (accountsResponse.getDealType().equals(DealType.LEARNING)) {
				studentResponse.addLearningSum(accountsResponse.getTotalPrice());
			} else if (accountsResponse.getDealType().equals(DealType.COUPON)) {
				studentResponse.addCouponSum(accountsResponse.getTotalPrice());
			} else if (accountsResponse.getDealType().equals(DealType.GAME)) {
				studentResponse.addGameSum(accountsResponse.getTotalPrice());
			} else if (accountsResponse.getDealType().equals(DealType.ETC)) {
				studentResponse.addEtcSum(accountsResponse.getTotalPrice());
			}

			if (accountsResponse.getAccountType().equals(AccountType.INCOME)) {
				studentResponse.addTotalIncome(accountsResponse.getTotalPrice());
			} else if (accountsResponse.getAccountType().equals(AccountType.EXPENDITURE)) {
				studentResponse.addTotalExpense(accountsResponse.getTotalPrice());
			}
		}
		studentResponse.setCouponsResponses(userCouponsResponses);
		studentResponse.setAccountsResponses(accountsResponses);

		return studentResponse;
	}
}

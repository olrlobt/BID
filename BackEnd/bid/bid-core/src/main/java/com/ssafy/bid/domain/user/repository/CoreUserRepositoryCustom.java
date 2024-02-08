package com.ssafy.bid.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AccountFindRequest;
import com.ssafy.bid.domain.user.dto.AccountFindResponse;
import com.ssafy.bid.domain.user.dto.AccountsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentFindRequest;
import com.ssafy.bid.domain.user.dto.StudentFindResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsFindResponse;

public interface CoreUserRepositoryCustom {
	Optional<User> findById(String id);

	Optional<StudentFindResponse> findStudentByUserNo(int userNo);

	List<UserCouponsFindResponse> findUserCouponsByUserNo(int userNo);

	List<AccountsFindResponse> findAccountsByUserNo(int userNo, StudentFindRequest studentFindRequest);

	List<AccountFindResponse> findAccountByUserNo(int userNo, AccountFindRequest accountFindRequest);
}

package com.ssafy.bid.domain.user.repository;

import com.ssafy.bid.domain.user.dto.*;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    List<StudentsResponse> findStudents(int gradeNo);

    List<UserCouponsResponse> findUserCoupons(int userNo);

    List<AccountsResponse> findAccounts(int userNo, StudentRequest studentRequest);

    Optional<StudentResponse> findStudent(int userNo);

    List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest);
}

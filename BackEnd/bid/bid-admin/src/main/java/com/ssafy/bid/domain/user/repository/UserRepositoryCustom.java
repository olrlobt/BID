package com.ssafy.bid.domain.user.repository;

import com.ssafy.bid.domain.user.dto.*;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.user.School;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.User;
import com.ssafy.bid.domain.user.dto.AccountRequest;
import com.ssafy.bid.domain.user.dto.AccountResponse;
import com.ssafy.bid.domain.user.dto.AccountsResponse;
import com.ssafy.bid.domain.user.dto.BallsResponse;
import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;
import com.ssafy.bid.domain.user.dto.StudentsResponse;
import com.ssafy.bid.domain.user.dto.UserCouponsResponse;

public interface UserRepositoryCustom {
    List<StudentsResponse> findStudents(int gradeNo);

    List<UserCouponsResponse> findUserCoupons(int userNo);

    List<AccountsResponse> findAccounts(int userNo, StudentRequest studentRequest);

    Optional<StudentResponse> findStudent(int userNo);

	List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest);

	List<School> findByNameContaining(String name);

	boolean checkExistsById(String id);

	Optional<School> findByCode(String code);

	List<Student> findAllByIds(List<Integer> userNos);

	List<BallsResponse> findBalls(int gradeNo);

	void resetBallCounts(int gradeNo);

	Optional<User> findByNameAndTel(String name, String tel);
}

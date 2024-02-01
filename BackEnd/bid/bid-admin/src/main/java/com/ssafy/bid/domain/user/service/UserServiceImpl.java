package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.*;
import com.ssafy.bid.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

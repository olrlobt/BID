package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.*;

import java.util.List;

public interface UserService {
    List<StudentsResponse> findStudents(int gradeNo);

    StudentResponse findStudent(int userNo, StudentRequest studentRequest);

    List<AccountResponse> findAccount(int userNo, AccountRequest accountRequest);
}

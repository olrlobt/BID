package com.ssafy.bid.domain.user.service;

import com.ssafy.bid.domain.user.dto.StudentRequest;
import com.ssafy.bid.domain.user.dto.StudentResponse;

public interface UserService {
	StudentResponse findStudent(int userNo, StudentRequest studentRequest);
}

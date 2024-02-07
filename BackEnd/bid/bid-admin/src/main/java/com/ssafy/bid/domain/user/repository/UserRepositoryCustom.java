package com.ssafy.bid.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolResponse;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;

public interface UserRepositoryCustom {
	List<StudentsFindResponse> findAllStudentByGradeNo(int gradeNo);

	List<SchoolResponse> findByNameContaining(String name);

	boolean checkExistsById(String id);

	List<Student> findAllByIds(List<Integer> userNos);

	List<BallsFindResponse> findAllBallsByGradeNo(int gradeNo);

	List<Student> findAllStudentsByGradeNo(int gradeNo);

	Optional<String> findIdByNameAndTel(String name, String tel);

	Optional<Admin> findAdminByUserNo(int userNo);
}

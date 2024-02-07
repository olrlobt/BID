package com.ssafy.bid.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentsFindResponse;

public interface UserRepositoryCustom {
	boolean checkExistsById(String id);

	List<SchoolsFindResponse> findSchoolsByName(String name);

	List<StudentsFindResponse> findAllStudentByGradeNo(int gradeNo);

	Optional<String> findUserIdByNameAndTel(String name, String tel);

	Optional<Admin> findAdminByUserNo(int userNo);

	List<Student> findAllByIds(List<Integer> userNos);

	List<BallsFindResponse> findAllBallsByGradeNo(int gradeNo);

	List<Student> findAllStudentsByGradeNo(int gradeNo);
}

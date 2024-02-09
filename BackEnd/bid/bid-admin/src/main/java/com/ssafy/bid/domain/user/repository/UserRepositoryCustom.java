package com.ssafy.bid.domain.user.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.user.Admin;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.dto.BallsFindResponse;
import com.ssafy.bid.domain.user.dto.SchoolsFindResponse;
import com.ssafy.bid.domain.user.dto.StudentsGetResponse;

public interface UserRepositoryCustom {
	boolean existsById(String id);

	Optional<Student> findStudentByUserNo(int userNo);

	List<SchoolsFindResponse> findSchoolsByName(String name);

	Optional<Admin> findAdminById(String id);

	boolean existsByIdAndTel(String id, String tel);

	List<StudentsGetResponse> findAllStudentByGradeNo(int gradeNo);

	Optional<String> findUserIdByNameAndTel(String name, String tel);

	Optional<Admin> findAdminByUserNo(int userNo);

	List<Student> findAllByIds(List<Integer> userNos);

	List<BallsFindResponse> findAllBallsByGradeNo(int gradeNo);

	List<Student> findAllStudentsByGradeNo(int gradeNo);
}

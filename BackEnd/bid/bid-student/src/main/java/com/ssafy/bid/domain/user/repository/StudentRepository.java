package com.ssafy.bid.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	Optional<Student> findStudentById(String id);
}

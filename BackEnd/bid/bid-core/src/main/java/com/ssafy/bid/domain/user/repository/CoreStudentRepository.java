package com.ssafy.bid.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.Student;

public interface CoreStudentRepository extends JpaRepository<Student, Integer> {
}

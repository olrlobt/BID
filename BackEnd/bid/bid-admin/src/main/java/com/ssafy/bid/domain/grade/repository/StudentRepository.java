package com.ssafy.bid.domain.grade.repository;

import com.ssafy.bid.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

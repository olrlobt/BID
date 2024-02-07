package com.ssafy.bid.domain.user.repository;

import com.ssafy.bid.domain.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findStudentById(String id);
}

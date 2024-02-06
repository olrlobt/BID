package com.ssafy.bid.domain.user.repository;

import com.ssafy.bid.domain.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    Optional<Grade> findByNo(Integer gradeNo);
}

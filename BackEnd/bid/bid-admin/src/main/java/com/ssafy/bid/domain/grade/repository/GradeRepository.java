package com.ssafy.bid.domain.grade.repository;

import com.ssafy.bid.domain.grade.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}

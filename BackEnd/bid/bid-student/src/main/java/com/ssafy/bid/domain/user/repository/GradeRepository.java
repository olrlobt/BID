package com.ssafy.bid.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.grade.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}

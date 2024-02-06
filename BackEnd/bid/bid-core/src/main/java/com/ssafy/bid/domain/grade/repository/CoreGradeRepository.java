package com.ssafy.bid.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.grade.Grade;

public interface CoreGradeRepository extends JpaRepository<Grade, Integer>, CoreGradeRepositoryCustom {
}

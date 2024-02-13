package com.ssafy.bid.domain.grade.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradeProjection;

public interface GradeRepository extends JpaRepository<Grade, Integer>, GradeRepositoryCustom {
	Optional<GradeProjection> findByNo(Integer no);
}

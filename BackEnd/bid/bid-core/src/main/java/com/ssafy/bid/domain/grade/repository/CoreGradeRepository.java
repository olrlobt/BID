package com.ssafy.bid.domain.grade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.grade.dto.GradeProjection;

public interface CoreGradeRepository extends JpaRepository<Grade, Integer>, CoreGradeRepositoryCustom {
	List<GradeProjection> findBy();
}

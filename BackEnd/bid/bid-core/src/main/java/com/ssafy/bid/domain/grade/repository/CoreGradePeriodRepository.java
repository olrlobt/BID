package com.ssafy.bid.domain.grade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.gradeperiod.GradePeriod;

public interface CoreGradePeriodRepository
	extends JpaRepository<GradePeriod, Integer>, CoreGradePeriodRepositoryCustom {
}

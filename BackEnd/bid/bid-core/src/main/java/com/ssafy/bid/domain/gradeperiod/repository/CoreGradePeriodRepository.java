package com.ssafy.bid.domain.gradeperiod.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.gradeperiod.GradePeriod;

import jakarta.validation.constraints.NotNull;

public interface CoreGradePeriodRepository
	extends JpaRepository<GradePeriod, Integer>, CoreGradePeriodRepositoryCustom {
	GradePeriod findByGradeNoAndSequence(@NotNull Integer gradeNo, @NotNull Integer sequence);

}

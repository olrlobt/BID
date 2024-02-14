package com.ssafy.bid.domain.gradeperiod.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.gradeperiod.GradePeriod;

public interface GradePeriodRepository extends JpaRepository<GradePeriod, Integer> {
	List<GradePeriod> findAllByGradeNo(int gradeNo);

	void deleteByGradeNo(int gradeNo);
}

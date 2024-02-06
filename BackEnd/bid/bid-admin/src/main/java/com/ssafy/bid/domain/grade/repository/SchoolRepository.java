package com.ssafy.bid.domain.grade.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {
	Optional<School> findByCode(String code);

}

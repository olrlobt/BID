package com.ssafy.bid.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {
}

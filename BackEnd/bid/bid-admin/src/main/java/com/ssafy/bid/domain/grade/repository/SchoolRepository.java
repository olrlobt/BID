package com.ssafy.bid.domain.grade.repository;

import com.ssafy.bid.domain.user.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer> {
    Optional<School> findByCode(String code);

}

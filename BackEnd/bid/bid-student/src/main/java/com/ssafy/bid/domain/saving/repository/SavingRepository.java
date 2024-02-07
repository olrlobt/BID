package com.ssafy.bid.domain.saving.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.saving.Saving;

public interface SavingRepository extends JpaRepository<Saving, Integer> {
}

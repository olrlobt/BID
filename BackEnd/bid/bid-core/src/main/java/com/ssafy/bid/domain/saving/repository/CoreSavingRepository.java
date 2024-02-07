package com.ssafy.bid.domain.saving.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.saving.Saving;

public interface CoreSavingRepository extends JpaRepository<Saving, Integer>, CoreSavingRepositoryCustom {
}

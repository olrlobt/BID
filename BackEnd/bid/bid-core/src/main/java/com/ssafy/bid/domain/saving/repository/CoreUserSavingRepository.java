package com.ssafy.bid.domain.saving.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.saving.UserSaving;

public interface CoreUserSavingRepository extends JpaRepository<UserSaving, Integer>, CoreUserSavingRepositoryCustom {
}

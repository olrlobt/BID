package com.ssafy.bid.domain.saving.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.saving.UserSaving;

public interface UserSavingRepository extends JpaRepository<UserSaving, Integer>, UserSavingRepositoryCustom {
	void deleteByUserNoAndSavingNo(int userNo, int savingNo);
}

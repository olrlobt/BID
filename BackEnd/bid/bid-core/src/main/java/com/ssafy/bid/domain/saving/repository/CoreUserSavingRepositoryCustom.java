package com.ssafy.bid.domain.saving.repository;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;

public interface CoreUserSavingRepositoryCustom {
	List<SavingExpireRequest> findAllSavingExpireInfos();
}

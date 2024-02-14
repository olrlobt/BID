package com.ssafy.bid.domain.saving.repository;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;

public interface CoreUserSavingRepositoryCustom {
	List<SavingExpireRequest> findAllSavingExpireInfos();

	List<SavingTransferAlertRequest> findAllSavingTransferInfos();
}

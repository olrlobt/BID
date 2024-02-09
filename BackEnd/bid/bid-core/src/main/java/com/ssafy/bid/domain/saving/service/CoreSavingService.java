package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;

public interface CoreSavingService {
	List<SavingExpireAlertRequest> expire();

	List<SavingTransferAlertRequest> findAllSavingTransferInfos();
}

package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;

public interface CoreSavingService {
	List<SavingExpireAlertRequest> expire();
}

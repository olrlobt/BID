package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

public interface SavingService {

	void saveSavings(CustomUserInfo userInfo, SavingSaveRequest savingSaveRequest);

	void deleteSavings(int userNo, int savingNo);

	List<SavingTransferAlertRequest> findAllSavingTransferInfos();

	void transfer();

	List<SavingExpireAlertRequest> expire();
}

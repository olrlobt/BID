package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

public interface SavingService {
	List<UserSavingListGetResponse> getAllSavings(int gradeNo, int userNo);

	void saveSavings(CustomUserInfo userInfo, SavingSaveRequest savingSaveRequest);

	void deleteSavings(int userNo, int savingNo);

	List<SavingTransferAlertRequest> findAllSavingTransferInfos();

	void transfer();
}

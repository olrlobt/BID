package com.ssafy.bid.domain.saving.service;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

public interface SavingService {
	List<UserSavingListGetResponse> getAllSavings(CustomUserInfo userInfo);

	void saveSavings(CustomUserInfo userInfo, SavingSaveRequest savingSaveRequest);

	void deleteSavings(CustomUserInfo userInfo, int savingNo);
}

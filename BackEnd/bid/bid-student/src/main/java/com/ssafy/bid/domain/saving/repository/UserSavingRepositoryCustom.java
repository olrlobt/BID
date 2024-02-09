package com.ssafy.bid.domain.saving.repository;

import java.util.List;

import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.dto.UserSavingListGetResponse;

public interface UserSavingRepositoryCustom {
	List<UserSavingListGetResponse> findAllByUserNoAndGradeNo(int userNo, int gradeNo);

	List<SavingTransferAlertRequest> findAllSavingTransferInfos();
}

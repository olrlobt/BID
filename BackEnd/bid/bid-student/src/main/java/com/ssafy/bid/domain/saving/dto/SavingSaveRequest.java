package com.ssafy.bid.domain.saving.dto;

import java.time.LocalDateTime;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingSaveRequest {
	private int no;

	public UserSaving toEntity(Saving saving, CustomUserInfo userInfo) {
		return UserSaving.builder()
			.startPeriod(LocalDateTime.now())
			.endPeriod(LocalDateTime.now().plusDays(saving.getDepositPeriod()))
			.currentPrice(0)
			.userNo(userInfo.getNo())
			.savingNo(no)
			.gradeNo(userInfo.getGradeNo())
			.build();
	}
}

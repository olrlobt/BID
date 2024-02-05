package com.ssafy.bid.domain.saving.dto;

import java.time.LocalDateTime;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.UserSaving;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingRequest {
	private int no;

	public UserSaving toEntity(Saving saving, int userNo) {
		return UserSaving.builder()
			.startPeriod(LocalDateTime.now())
			.endPeriod(LocalDateTime.now().plusDays(saving.getDepositPeriod()))
			.currentPrice(0)
			.userNo(userNo)
			.savingNo(no)
			.build();
	}
}

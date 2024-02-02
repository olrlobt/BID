package com.ssafy.bid.domain.saving.dto;

import java.time.LocalDateTime;

import com.ssafy.bid.domain.saving.UserSaving;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SavingRequest {
	private int no;
	private int depositPeriod;
	private int depositCycle;
	private int depositPrice;
	private int interestRate;

	public UserSaving toEntity(int userNo) {
		return UserSaving.builder()
			.startPeriod(LocalDateTime.now())
			.endPeriod(LocalDateTime.now().plusDays(depositPeriod))
			.resultPrice((depositPeriod / depositCycle * depositPrice) * (1 + interestRate / 100))
			.userNo(userNo)
			.savingNo(no)
			.build();
	}
}

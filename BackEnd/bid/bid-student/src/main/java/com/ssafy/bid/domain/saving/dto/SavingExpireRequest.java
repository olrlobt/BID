package com.ssafy.bid.domain.saving.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ssafy.bid.domain.user.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingExpireRequest {
	private int userSavingNo;
	private LocalDate userSavingEndPeriod;
	private int resultPrice;
	private Student student;

	public SavingExpireRequest(
		int userSavingNo,
		LocalDateTime userSavingEndPeriod,
		int resultPrice,
		Student student
	) {
		this.userSavingNo = userSavingNo;
		this.userSavingEndPeriod = userSavingEndPeriod.toLocalDate();
		this.resultPrice = resultPrice;
		this.student = student;
	}
}

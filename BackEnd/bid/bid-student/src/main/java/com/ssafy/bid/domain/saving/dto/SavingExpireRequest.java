package com.ssafy.bid.domain.saving.dto;

import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.user.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingExpireRequest {
	private UserSaving userSaving;
	private Student student;

	public SavingExpireRequest(
		UserSaving userSaving,
		Student student
	) {
		this.userSaving = userSaving;
		this.student = student;
	}
}

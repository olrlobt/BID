package com.ssafy.bid.domain.saving.dto;

import com.ssafy.bid.domain.user.Student;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingTransferRequest {
	private int price;
	private Student student;

	public SavingTransferRequest(
		int price,
		Student student
	) {
		this.price = price;
		this.student = student;
	}
}

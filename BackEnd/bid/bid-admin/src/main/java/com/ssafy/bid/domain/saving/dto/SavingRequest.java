package com.ssafy.bid.domain.saving.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SavingRequest {
	private int no;
	private int depositPrice;
	private int interestRate;
}

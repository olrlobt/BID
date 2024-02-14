package com.ssafy.bid.domain.saving.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaxRateListGetResponse {
	private int incomeLevel;
	private int startRange;
	private int endRange;
	private int taxRate;

	@Builder
	public TaxRateListGetResponse(
		int incomeLevel,
		int startRange,
		int endRange,
		int taxRate
	) {
		this.incomeLevel = incomeLevel;
		this.startRange = startRange;
		this.endRange = endRange;
		this.taxRate = taxRate;
	}
}

package com.ssafy.bid.domain.saving.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingFindResponse {
	private int asset;
	private int savingNo;
	private String savingName;
	private int savingDepositPeriod;
	private int savingDepositCycle;
	private int savingDepositPrice;
	private int savingInterestRate;
	private String savingTerms;

	public SavingFindResponse(
		int asset,
		int savingNo,
		String savingName,
		int savingDepositPeriod,
		int savingDepositCycle,
		int savingDepositPrice,
		int savingInterestRate,
		String savingTerms
	) {
		this.asset = asset;
		this.savingNo = savingNo;
		this.savingName = savingName;
		this.savingDepositPeriod = savingDepositPeriod;
		this.savingDepositCycle = savingDepositCycle;
		this.savingDepositPrice = savingDepositPrice;
		this.savingInterestRate = savingInterestRate;
		this.savingTerms = savingTerms;
	}
}

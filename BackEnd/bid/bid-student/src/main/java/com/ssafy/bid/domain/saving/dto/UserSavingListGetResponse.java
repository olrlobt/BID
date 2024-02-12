package com.ssafy.bid.domain.saving.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSavingListGetResponse {
	private int asset;
	private int savingNo;
	private String savingName;
	private int savingDepositPeriod;
	private int savingDepositCycle;
	private int savingDepositPrice;
	private int savingInterestRate;
	private String savingTerms;
	private boolean isMySaving;
	private int incomeLevel;
	private List<TaxRateListGetResponse> taxRateListGetResponses;

	public UserSavingListGetResponse(
		int asset,
		int savingNo,
		String savingName,
		int savingDepositPeriod,
		int savingDepositCycle,
		int savingDepositPrice,
		int savingInterestRate,
		String savingTerms,
		long countMySaving
	) {
		this.asset = asset;
		this.savingNo = savingNo;
		this.savingName = savingName;
		this.savingDepositPeriod = savingDepositPeriod;
		this.savingDepositCycle = savingDepositCycle;
		this.savingDepositPrice = savingDepositPrice;
		this.savingInterestRate = savingInterestRate;
		this.savingTerms = savingTerms;
		this.isMySaving = countMySaving >= 1;
	}

	public void setIncomeLevel(int incomeLevel) {
		this.incomeLevel = incomeLevel;
	}

	public void setTaxRateListGetResponses(List<TaxRateListGetResponse> taxRateListGetResponses) {
		this.taxRateListGetResponses = taxRateListGetResponses;
	}
}

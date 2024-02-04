package com.ssafy.bid.domain.saving.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SavingModifyRequest {
	private List<SavingRequest> savingRequests;
}

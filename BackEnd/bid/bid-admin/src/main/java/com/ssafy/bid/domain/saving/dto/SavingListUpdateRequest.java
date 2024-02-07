package com.ssafy.bid.domain.saving.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SavingListUpdateRequest {
	private List<SavingUpdateRequest> savingUpdateRequests;
}

package com.ssafy.bid.domain.reward.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RewardSendRequest {
	private int no;
	private List<Integer> usersNos;
	private String comment;
}

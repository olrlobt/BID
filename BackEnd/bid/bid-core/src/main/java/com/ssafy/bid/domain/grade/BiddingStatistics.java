package com.ssafy.bid.domain.grade;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BiddingStatistics {
	private int countFourteenDaysAgo;
	private int countThirteenDaysAgo;
	private int countTwelveDaysAgo;
	private int countElevenDaysAgo;
	private int countTenDaysAgo;
	private int countNineDaysAgo;
	private int countEightDaysAgo;
	private int countSevenDaysAgo;
	private int countSixDaysAgo;
	private int countFiveDaysAgo;
	private int countFourDaysAgo;
	private int countThreeDaysAgo;
	private int countTwoDaysAgo;
	private int countOneDaysAgo;
}

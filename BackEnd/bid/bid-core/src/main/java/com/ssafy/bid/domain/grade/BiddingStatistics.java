package com.ssafy.bid.domain.grade;

import com.ssafy.bid.domain.grade.dto.WinningBiddingStatisticsFindResponse;

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

	public void updateBiddingStatistics(WinningBiddingStatisticsFindResponse response) {
		switch (response.getDaysBefore()) {
			case 14 -> this.countFourteenDaysAgo = response.getCount();
			case 13 -> this.countThirteenDaysAgo = response.getCount();
			case 12 -> this.countTwelveDaysAgo = response.getCount();
			case 11 -> this.countElevenDaysAgo = response.getCount();
			case 10 -> this.countTenDaysAgo = response.getCount();
			case 9 -> this.countNineDaysAgo = response.getCount();
			case 8 -> this.countEightDaysAgo = response.getCount();
			case 7 -> this.countSevenDaysAgo = response.getCount();
			case 6 -> this.countSixDaysAgo = response.getCount();
			case 5 -> this.countFiveDaysAgo = response.getCount();
			case 4 -> this.countFourDaysAgo = response.getCount();
			case 3 -> this.countThreeDaysAgo = response.getCount();
			case 2 -> this.countTwoDaysAgo = response.getCount();
			default -> this.countOneDaysAgo = response.getCount();
		}
	}
}

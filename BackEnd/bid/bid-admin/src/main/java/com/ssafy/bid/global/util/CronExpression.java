package com.ssafy.bid.global.util;

import java.time.LocalTime;

public class CronExpression {

	public static String of(LocalTime localTime) {
		return String.format("0 %d %d * * *", localTime.getMinute(), localTime.getHour());
	}
}

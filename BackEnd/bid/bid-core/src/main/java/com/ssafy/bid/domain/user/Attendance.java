package com.ssafy.bid.domain.user;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class Attendance {
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;

	public void checkAttendance() {
		int dayOfWeek = LocalDateTime.now().getDayOfWeek().getValue();
		if (dayOfWeek == 1) {
			monday = true;
		} else if (dayOfWeek == 2) {
			tuesday = true;
		} else if (dayOfWeek == 3) {
			wednesday = true;
		} else if (dayOfWeek == 4) {
			thursday = true;
		} else if (dayOfWeek == 5) {
			friday = true;
		} else {
			// TODO: 예외
		}
	}

	public int calculateSalary() {
		// TODO: 주급 계산하는 로직
		return 0;
	}
}

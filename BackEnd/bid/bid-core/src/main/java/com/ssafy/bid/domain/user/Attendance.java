package com.ssafy.bid.domain.user;

import java.time.LocalDateTime;

import com.ssafy.bid.global.error.exception.InvalidParameterException;

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
		switch (dayOfWeek) {
			case 1 -> monday = true;
			case 2 -> tuesday = true;
			case 3 -> wednesday = true;
			case 4 -> thursday = true;
			case 5 -> friday = true;
			default -> throw new InvalidParameterException("출석가능한 요일이 아님.", dayOfWeek);
		}
	}

	public int calculateSalary(int salary) {
		int fee = salary / 5;
		int result = salary;
		if (!monday) {
			result -= fee;
		}
		if (!tuesday) {
			result -= fee;
		}
		if (!wednesday) {
			result -= fee;
		}
		if (!thursday) {
			result -= fee;
		}
		if (!friday) {
			result -= fee;
		}
		reset();
		return result;
	}

	private void reset() {
		this.monday = false;
		this.tuesday = false;
		this.wednesday = false;
		this.thursday = false;
		this.friday = false;
	}
}

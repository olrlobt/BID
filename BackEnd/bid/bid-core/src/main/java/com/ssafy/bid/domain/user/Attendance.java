package com.ssafy.bid.domain.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Attendance {

	@ColumnDefault("false")
	private Boolean monday;

	@ColumnDefault("false")
	private Boolean tuesday;

	@ColumnDefault("false")
	private Boolean wednesday;

	@ColumnDefault("false")
	private Boolean thursday;

	@ColumnDefault("false")
	private Boolean friday;

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

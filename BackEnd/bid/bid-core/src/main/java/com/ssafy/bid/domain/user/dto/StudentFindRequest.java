package com.ssafy.bid.domain.user.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class StudentFindRequest {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private LocalDate endDate;

	public StudentFindRequest(
		LocalDate startDate,
		LocalDate endDate
	) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}

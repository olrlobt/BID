package com.ssafy.bid.domain.grade.dto;

import java.time.LocalTime;
import java.util.List;

import com.ssafy.bid.domain.grade.BiddingStatistics;
import com.ssafy.bid.domain.grade.ExpenditureStatistics;
import com.ssafy.bid.domain.grade.Grade;

import lombok.Getter;

@Getter
public class GradeSaveRequest {
	private String schoolCode;
	private int year;
	private int classRoom;
	private int userNo;
	private int schoolNo;
	private List<StudentListSaveRequest> studentListSaveRequests;

	public Grade toEntity() {
		return Grade.builder()
			.asset(0)
			.schoolCode(schoolCode)
			.year(year)
			.classRoom(classRoom)
			.salary(20000)
			.salaryRecommendation(20000)
			.transferAlertPeriod(LocalTime.of(8, 50, 0))
			.transferPeriod(LocalTime.of(15, 0, 0))
			.expenditureStatistics(new ExpenditureStatistics())
			.biddingStatistics(new BiddingStatistics())
			.userNo(userNo)
			.build();
	}
}

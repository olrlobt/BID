package com.ssafy.bid.domain.grade.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;

@Getter
public class GradeListGetResponse {
	private int no;
	private String schoolName;
	private int year;
	private int classRoom;
	private int createdAt;
	private boolean isMain;

	public GradeListGetResponse(
		int no,
		String schoolName,
		int year,
		int classRoom,
		LocalDateTime createdAt,
		int mainGradeNo
	) {
		this.no = no;
		this.schoolName = schoolName;
		this.year = year;
		this.classRoom = classRoom;
		DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.createdAt = createdAt.getYear();
		this.isMain = this.no == mainGradeNo;
	}
}

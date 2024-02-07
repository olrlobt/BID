package com.ssafy.bid.domain.grade.dto;

import lombok.Getter;

@Getter
public class GradeListGetResponse {
	private int no;
	private String schoolName;
	private int year;
	private int classRoom;

	public GradeListGetResponse(
		int no,
		String schoolName,
		int year,
		int classRoom
	) {
		this.no = no;
		this.schoolName = schoolName;
		this.year = year;
		this.classRoom = classRoom;
	}
}

package com.ssafy.bid.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo {
	private int no;
	private int gradeNo;
	private String name;
	private String profileImgUrl;
}

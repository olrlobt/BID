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
	private String schoolName;
	private int asset;

	public StudentInfo(
		int no,
		int gradeNo,
		String name,
		String profileImgUrl,
		int asset
	) {
		this.no = no;
		this.gradeNo = gradeNo;
		this.name = name;
		this.profileImgUrl = profileImgUrl;
		this.asset = asset;
	}
}

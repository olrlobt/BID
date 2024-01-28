package com.ssafy.bid.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentsResponse {
	private Integer no;
	private String number;
	private String name;
	private Integer asset;

	public StudentsResponse(
		Integer no,
		String id,
		String name,
		Integer asset
	) {
		this.no = no;
		this.number = id.split("-")[2].strip(); // TODO: 학년-반-번호 형식으로 id 지정할 것인지 여부 정하기
		this.name = name;
		this.asset = asset;
	}
}

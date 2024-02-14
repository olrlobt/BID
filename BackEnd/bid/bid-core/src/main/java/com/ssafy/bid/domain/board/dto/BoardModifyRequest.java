package com.ssafy.bid.domain.board.dto;

import com.ssafy.bid.domain.board.Category;

import lombok.Getter;

@Getter
public class BoardModifyRequest {

	private String title;
	private String description;
	private Category category;
}

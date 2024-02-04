package com.ssafy.bid.domain.board.repository;

import java.util.List;

import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardResponse;

public interface BoardCustomRepository {

	List<BoardResponse> findBoards(int gradeNo, Category category, String keyword);
}

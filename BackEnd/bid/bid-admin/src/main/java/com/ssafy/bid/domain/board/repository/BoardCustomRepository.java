package com.ssafy.bid.domain.board.repository;

import java.util.List;

import com.ssafy.bid.domain.board.dto.BoardResponse;

public interface BoardCustomRepository {

	List<BoardResponse> findAllStudentBoards(int gradeNo);

}
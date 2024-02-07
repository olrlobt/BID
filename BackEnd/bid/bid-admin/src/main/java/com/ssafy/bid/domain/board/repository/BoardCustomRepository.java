package com.ssafy.bid.domain.board.repository;

import java.util.List;

import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.BoardResponse;

public interface BoardCustomRepository {

	List<BoardListResponse> findAllStudentBoards(int gradeNo);

}
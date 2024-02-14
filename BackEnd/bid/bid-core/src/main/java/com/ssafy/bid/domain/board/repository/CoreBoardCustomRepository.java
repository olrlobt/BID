package com.ssafy.bid.domain.board.repository;

import java.util.Optional;

import com.ssafy.bid.domain.board.dto.BoardResponse;

public interface CoreBoardCustomRepository {
	Optional<BoardResponse> getStudentBoard(long boardNo, int gradeNo);
}

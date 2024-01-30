package com.ssafy.bid.domain.board.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.bid.domain.board.dto.BoardResponse;

public interface BoardCustomRepository {

	List<BoardResponse> findAllStudentBoards(int gradeNo);

	Optional<BoardResponse> getStudentBoard(int gradeNo, long boardNo);
}
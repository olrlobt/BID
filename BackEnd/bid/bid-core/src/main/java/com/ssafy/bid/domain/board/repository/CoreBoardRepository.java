package com.ssafy.bid.domain.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.BoardStatus;

import jakarta.validation.constraints.NotNull;

public interface CoreBoardRepository extends JpaRepository<Board, Long>, CoreBoardCustomRepository {
	List<Board> findAllByBoardStatus(@NotNull BoardStatus boardStatus);
}

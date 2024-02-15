package com.ssafy.bid.domain.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Board;

import jakarta.validation.constraints.NotNull;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

	List<Board> findAllByGradePeriodNo(@NotNull Integer gradePeriodNo);

}

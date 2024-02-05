package com.ssafy.bid.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Board;

public interface CoreBoardRepository extends JpaRepository<Board, Long>, CoreBoardCustomRepository {
}

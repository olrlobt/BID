package com.ssafy.bid.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {

}

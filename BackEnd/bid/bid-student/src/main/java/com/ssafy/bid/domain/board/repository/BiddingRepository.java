package com.ssafy.bid.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Bidding;

import jakarta.validation.constraints.NotNull;

public interface BiddingRepository extends JpaRepository<Bidding, Integer> {

	Bidding findByUserNoAndBoardNo(@NotNull Integer userNo, @NotNull Long boardNo);

}

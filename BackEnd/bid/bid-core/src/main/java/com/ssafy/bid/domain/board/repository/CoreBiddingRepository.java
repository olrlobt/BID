package com.ssafy.bid.domain.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Bidding;

import jakarta.validation.constraints.NotNull;

public interface CoreBiddingRepository extends JpaRepository<Bidding, Long> {

	Optional<Bidding> findByUserNoAndBoardNo(@NotNull Integer userNo, @NotNull Long boardNo);
}

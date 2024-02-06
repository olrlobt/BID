package com.ssafy.bid.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Bidding;

public interface BiddingRepository extends JpaRepository<Bidding, Integer> {
}

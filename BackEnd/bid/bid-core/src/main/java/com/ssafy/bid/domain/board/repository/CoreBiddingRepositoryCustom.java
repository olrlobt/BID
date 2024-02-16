package com.ssafy.bid.domain.board.repository;

import java.util.List;

import com.ssafy.bid.domain.board.Bidding;

public interface CoreBiddingRepositoryCustom {
	List<Bidding> findAllByBoardNoLimit(long boardNo);
}

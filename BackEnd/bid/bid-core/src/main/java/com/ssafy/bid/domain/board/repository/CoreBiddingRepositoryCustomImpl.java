package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBidding.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.Bidding;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreBiddingRepositoryCustomImpl implements CoreBiddingRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Bidding> findAllByBoardNoLimit(long boardNo) {
		return queryFactory
			.selectFrom(bidding)
			.where(bidding.boardNo.eq(boardNo))
			.orderBy(bidding.price.desc())
			.limit(6)
			.fetch();
	}
}

package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBidding.*;
import static com.ssafy.bid.domain.board.QBoard.*;
import static com.ssafy.bid.domain.user.QStudent.*;
import static com.ssafy.bid.domain.user.QUser.*;

import java.util.List;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.BiddingStatus;
import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.user.QUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardCustomRepository {

	private final JPAQueryFactory queryFactory;

	private final ConstructorExpression<BoardListResponse> constructor = Projections.constructor(
		BoardListResponse.class,
		board.no,
		board.title,
		board.boardStatus,
		new CaseBuilder()
			.when(board.attendeeCount.eq(0))
			.then(board.startPrice)
			.when(board.boardStatus.eq(BoardStatus.COMPLETED))
			.then(board.resultPrice)
			.otherwise(board.totalPrice.divide(board.attendeeCount)),
		board.category,
		board.goodsImgUrl,
		user.name,
		board.gradePeriodNo
	);

	@Override
	public List<BoardListResponse> findBoards(int gradeNo) {
		return queryFactory.select(constructor)
			.from(board)
			.innerJoin(user)
			.on(board.userNo.eq(user.no)
				.and(board.gradeNo.eq(gradeNo))
				.and(board.boardStatus.eq(BoardStatus.PROGRESS)))
			.orderBy(board.createdAt.desc())
			.limit(20)
			.fetch();
	}

	@Override
	public List<BoardListResponse> findMyBoards(int userNo) {
		return queryFactory.select(constructor)
			.from(board)
			.innerJoin(student).on(board.userNo.eq(student.no).and(student.no.eq(userNo)))
			.orderBy(board.createdAt.desc())
			.limit(20)
			.fetch();
	}

	@Override
	public List<BoardListResponse> findMyBiddingBoards(int userNo) {
		return queryFactory.select(constructor)
			.from(board)
			.innerJoin(bidding).on(bidding.boardNo.eq(board.no).and(bidding.biddingStatus.eq(BiddingStatus.BIDDING)))
			.innerJoin(student).on(student.no.eq(board.userNo))
			.orderBy(board.createdAt.desc())
			.limit(20)
			.fetch();
	}
}

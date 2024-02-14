package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBoard.*;
import static com.ssafy.bid.domain.user.QUser.*;

import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.dto.BoardResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreBoardRepositoryImpl implements CoreBoardCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<BoardResponse> getStudentBoard(long boardNo, int gradeNo) {

		return Optional.ofNullable(
			queryFactory.select(Projections.constructor(BoardResponse.class,
					board.no,
					board.title,
					board.description,
					board.startPrice,
					board.boardStatus,
					board.totalPrice.divide(board.attendeeCount),
					board.resultPrice,
					board.category,
					board.goodsImgUrl,
					user.no,
					user.name,
					user.profileImgUrl,
					board.gradePeriodNo,
					board.createdAt
				))
				.from(board)
				.innerJoin(user)
				.on(board.userNo.eq(user.no)
					.and(board.gradeNo.eq(gradeNo))
					.and(board.no.eq(boardNo)))
				.fetchOne());
	}
}

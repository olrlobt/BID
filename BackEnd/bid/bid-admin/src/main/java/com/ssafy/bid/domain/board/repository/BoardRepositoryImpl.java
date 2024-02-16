package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBoard.*;
import static com.ssafy.bid.domain.user.QUser.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.dto.BoardListResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardListResponse> findAllStudentBoards(int gradeNo, int userGradeNo) {
		return queryFactory.select(Projections.constructor(BoardListResponse.class,
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
			))
			.from(board)
			.innerJoin(user)
			.on(board.userNo.eq(user.no)
				.and(board.gradeNo.eq(gradeNo))
				.and(board.boardStatus.eq(BoardStatus.PROGRESS)))
			.orderBy(board.createdAt.desc())
			.limit(20)
			.fetch();
	}
}

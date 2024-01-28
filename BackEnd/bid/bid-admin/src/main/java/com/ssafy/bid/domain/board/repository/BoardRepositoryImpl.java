package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBoard.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.dto.BoardResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardResponse> findAllStudentBoards(int gradeNo) {
		return queryFactory.select(Projections.constructor(BoardResponse.class,
				board.no,
				board.title,
				board.boardStatus,
				board.totalPrice.divide(board.attendeeCount),
				board.resultPrice,
				board.goodsImgUrl,
				student.name
			))
			.from(board)
			.innerJoin(student).on(board.userNo.eq(student.no))
			.where(student.gradeNo.eq(gradeNo))
			.orderBy(board.createdAt.desc())
			.fetch();
	}

	@Override
	public BoardResponse getStudentBoard(int gradeNo, long boardNo) {
		return queryFactory.select(Projections.constructor(BoardResponse.class,
				board.no,
				board.title,
				board.description,
				board.startPrice,
				board.boardStatus,
				board.totalPrice.divide(board.attendeeCount),
				board.resultPrice,
				board.category,
				board.goodsImgUrl,
				student.name,
				board.gradePeriodNo
			))
			.from(board)
			.innerJoin(student).on(board.userNo.eq(student.no))
			.where(student.gradeNo.eq(gradeNo).and(board.no.eq(boardNo)))
			.fetchOne();
	}

}
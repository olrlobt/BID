package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBoard.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.Optional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.dto.BoardResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreBoardRepositoryImpl implements CoreBoardCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<BoardResponse> getStudentBoard(long boardNo) {

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
					student.no,
					student.name,
					student.profileImgUrl,
					board.gradePeriodNo,
					board.createdAt
				))
				.from(board)
				.innerJoin(student)
				.on(board.userNo.eq(student.no)
					.and(board.gradeNo.eq(student.gradeNo))
					.and(board.no.eq(boardNo)))
				.fetchOne());
	}
}

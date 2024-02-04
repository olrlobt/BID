package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QBoard.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardCustomRepository{

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BoardResponse> findBoards(int gradeNo, Category category, String keyword) {

		return queryFactory.select(Projections.constructor(BoardResponse.class,
				board.no,
				board.title,
				board.boardStatus,
				board.totalPrice.divide(board.attendeeCount),
				board.resultPrice,
				board.goodsImgUrl,
				student.name))
			.from(board)
			.innerJoin(student).on(board.userNo.eq(student.no).and(student.gradeNo.eq(gradeNo)))
			.where(board.category.eq(category)
				.and(board.title.contains(keyword)
					.or(board.description.contains(keyword))))
			.orderBy(board.createdAt.desc())
			.fetch();
	}
}

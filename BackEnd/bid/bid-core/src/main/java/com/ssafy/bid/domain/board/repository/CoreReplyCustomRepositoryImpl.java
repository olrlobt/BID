package com.ssafy.bid.domain.board.repository;

import static com.ssafy.bid.domain.board.QReply.*;
import static com.ssafy.bid.domain.user.QStudent.*;

import java.util.List;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.bid.domain.board.dto.ReplyResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoreReplyCustomRepositoryImpl implements CoreReplyCustomRepository {

	private final JPAQueryFactory queryFactory;

	public List<ReplyResponse> findReplies(long boardNo) {
		return queryFactory.select(Projections.constructor(ReplyResponse.class,
				reply.no,
				reply.content,
				student.name,
				student.profileImgUrl,
				reply.createdAt
			))
			.from(reply)
			.innerJoin(student)
			.on(reply.boardNo.eq(boardNo).and(reply.userNo.eq(student.no)))
			.orderBy(reply.createdAt.asc())
			.fetch();
	}
}

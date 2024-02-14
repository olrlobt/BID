package com.ssafy.bid.domain.board.repository;

import java.util.List;

import com.ssafy.bid.domain.board.dto.ReplyResponse;

public interface CoreReplyCustomRepository {
	List<ReplyResponse> findReplies(long boardNo);
}

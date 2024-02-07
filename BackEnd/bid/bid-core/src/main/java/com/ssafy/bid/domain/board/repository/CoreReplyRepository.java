package com.ssafy.bid.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Reply;

public interface CoreReplyRepository extends JpaRepository<Reply, Long>, CoreReplyCustomRepository {
}

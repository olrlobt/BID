package com.ssafy.bid.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.board.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}

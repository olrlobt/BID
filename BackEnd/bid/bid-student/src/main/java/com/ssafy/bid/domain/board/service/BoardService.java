package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.Reply;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.dto.ReplyCreateRequest;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;

	public List<BoardResponse> findBoards(int gradeNo, String keyword) {
		return boardRepository.findBoards(gradeNo, keyword);
	}

	public MyBoardsResponse findMyAllBoards(int userNo) {

		List<BoardResponse> myBoards = boardRepository.findMyBoards(userNo);
		List<BoardResponse> myBiddingBoards = boardRepository.findMyBiddingBoards(userNo);

		return new MyBoardsResponse(myBoards, myBiddingBoards);
	}

	@Transactional
	public void addBoard(int userNo, int gradeNo, BoardCreateRequest boardCreateRequest) {
		Board board = boardCreateRequest.toEntity(1, 1);
		boardRepository.save(board);
	}

	@Transactional
	public void deleteBoard(long boardNo) {
		if (!boardRepository.existsById(boardNo)) {
			throw new EntityNotFoundException("게시물이 없습니다.");
		}
		boardRepository.deleteById(boardNo);
	}

	@Transactional
	public void addBoardReply(int userNo, int boardNo, ReplyCreateRequest replyCreateRequest) {
		replyCreateRequest.setUserNo(userNo);
		replyCreateRequest.setBoardNo(boardNo);
		Reply reply = replyCreateRequest.toEntity();
		replyRepository.save(reply);
	}
}

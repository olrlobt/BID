package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Bidding;
import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.Reply;
import com.ssafy.bid.domain.board.dto.BiddingCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.dto.ReplyCreateRequest;
import com.ssafy.bid.domain.board.repository.BiddingRepository;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

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
	private final BiddingRepository biddingRepository;

	public List<BoardListResponse> findBoards(int gradeNo) {
		//학생 gradeNO과 gradeNo이 맞는지 확인
		return boardRepository.findBoards(gradeNo);
	}

	public MyBoardsResponse findAllBoardsByUserNo(int userNo) {
		//조회하는 유저가 조회된 유저의 gradeNo과 맞는지 확인

		List<BoardListResponse> myBoards = boardRepository.findMyBoards(userNo);
		List<BoardListResponse> myBiddingBoards = boardRepository.findMyBiddingBoards(userNo);

		return new MyBoardsResponse(myBoards, myBiddingBoards);
	}

	@Transactional
	public void addBoard(int userNo, int gradeNo, BoardCreateRequest boardCreateRequest) {


		Board board = boardCreateRequest.toEntity(1, 1);
		boardRepository.save(board);
	}

	@Transactional
	public void deleteBoard(long boardNo) {
		// 해당 게시글이 user의 것인지 찾는 로직 추가

		if (!boardRepository.existsById(boardNo)) {
			throw new ResourceNotFoundException("해당 게시물이 없습니다.", boardNo);
		}
		boardRepository.deleteById(boardNo);
	}

	@Transactional
	public void addBoardReply(int userNo, int boardNo, ReplyCreateRequest replyCreateRequest) {
		// 해당 유저가 게시글 gradeNo이 맞는지 확인

		replyCreateRequest.setUserNo(userNo);
		replyCreateRequest.setBoardNo(boardNo);
		Reply reply = replyCreateRequest.toEntity();
		replyRepository.save(reply);
	}

	@Transactional
	public void modifyBoardReply(int userNo, int replyNo, ReplyCreateRequest replyCreateRequest) {
		// 댓글 수정은 사용하지 않는다.
		Reply reply = replyRepository.findById(replyNo)
			.orElseThrow();

		reply.modify(replyCreateRequest.getContent());
	}

	@Transactional
	public void deleteBoardReply(int userNo, int replyNo) {
		// 해당 유저가 댓글 작성한 user가 맞나 확인

		if (!replyRepository.existsById(replyNo)) {
			throw new ResourceNotFoundException("댓글이 없습니다.", replyNo);
		}
		replyRepository.deleteById(replyNo);
	}

	@Transactional
	public void bidBoard(BiddingCreateRequest biddingCreateRequest, long boardNo, int gradeNo, int userNo) {


		biddingCreateRequest.setBoardNo(boardNo);
		biddingCreateRequest.setGradeNo(gradeNo);
		biddingCreateRequest.setUserNo(userNo);
		biddingRepository.save(biddingCreateRequest.toEntity());
	}

	@Transactional
	public void rebidBoard(BiddingCreateRequest biddingCreateRequest, long boardNo, int gradeNo, int userNo) {

		Bidding userBidding = biddingRepository.findByUserNoAndBoardNo(userNo, boardNo);
		userBidding.rebidding(biddingCreateRequest.getPrice());
	}
}

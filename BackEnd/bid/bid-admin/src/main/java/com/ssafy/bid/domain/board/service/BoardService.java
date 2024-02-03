package com.ssafy.bid.domain.board.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;

	public List<BoardResponse> findAllStudentBoards(int gradeNo){
		return boardRepository.findAllStudentBoards(gradeNo);
	}

	public BoardResponse getStudentBoard(int gradeNo, long boardNo){
		Optional<BoardResponse> studentBoard = boardRepository.getStudentBoard(gradeNo, boardNo);

		if (studentBoard.isEmpty()) {
			throw new NoSuchElementException("해당 게시글이 없습니다.");
		}
		return studentBoard.get();
	}

	@Transactional
	public void deleteBoard(long boardNo){
		if (!boardRepository.existsById(boardNo)) {
			throw new NoSuchElementException("해당 게시글이 없습니다.");
		}
		boardRepository.deleteById(boardNo);
	}


	@Transactional
	public void deleteReply(long replyNo){
		if (!replyRepository.existsById(replyNo)) {
			throw new NoSuchElementException("해당 댓글이 없습니다.");
		}
		replyRepository.deleteById(replyNo);
	}
}
package com.ssafy.bid.domain.board.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;

	public List<BoardListResponse> findAllStudentBoards(int gradeNo) {
		// user와 gradeNo이 다를 경우 error
		return boardRepository.findAllStudentBoards(gradeNo);
	}

	@Transactional
	public void deleteBoard(long boardNo, int gradeNo, int userNo) {

		// user와 gradeNo이 다를 경우 error

		if (!boardRepository.existsById(boardNo)) {
			throw new ResourceNotFoundException("해당 게시글이 없습니다.", boardNo);
		}
		boardRepository.deleteById(boardNo);
	}

	@Transactional
	public void deleteReply(long replyNo, int gradeNo, int userNo) {

		// user와 gradeNo 이 다른 경우 error

		if (!replyRepository.existsById(replyNo)) {
			throw new ResourceNotFoundException("해당 댓글이 없습니다.", replyNo);
		}
		replyRepository.deleteById(replyNo);
	}
}

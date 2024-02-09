package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.service.BoardService;
import com.ssafy.bid.domain.board.service.CoreBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardApi {

	private final BoardService boardService;
	private final CoreBoardService coreBoardService;

	@GetMapping("/{gradeNo}/boards")
	public List<BoardListResponse> findAllStudentBoards(@PathVariable int gradeNo) {
		return boardService.findAllStudentBoards(gradeNo);
	}

	@GetMapping("/{gradeNo}/boards/{boardNo}")
	public ResponseEntity<BoardResponse> getBoardDetail(@PathVariable int gradeNo, @PathVariable long boardNo) {
		int userNo = 21; // security
		BoardResponse boardResponse = coreBoardService.getBoardDetail(userNo, boardNo, gradeNo);
		return ResponseEntity.ok(boardResponse);
	}

	@DeleteMapping("/{gradeNo}/boards/{boardNo}")
	public ResponseEntity<?> deleteBoard(@PathVariable int gradeNo, @PathVariable long boardNo) {
		int userNo = 21; // security
		boardService.deleteBoard(boardNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{gradeNo}/boards/{boardNo}/{replyNo}")
	public ResponseEntity<?> deleteReply(@PathVariable int gradeNo,
		@PathVariable long boardNo,
		@PathVariable long replyNo) {
		int userNo = 21; // security
		boardService.deleteReply(replyNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}
}

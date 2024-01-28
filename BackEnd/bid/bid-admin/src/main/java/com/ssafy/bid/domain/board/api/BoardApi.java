package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardApi {

	private final BoardService boardService;

	@GetMapping("/{gradeNo}/boards")
	public List<BoardResponse> findAllStudentBoards(@PathVariable int gradeNo){
		return boardService.findAllStudentBoards(gradeNo);
	}

	@GetMapping("/{gradeNo}/boards/{boardNo}")
	public ResponseEntity<BoardResponse> getStudentBoard(@PathVariable int gradeNo, @PathVariable long boardNo) {
		BoardResponse boardResponse = boardService.getStudentBoard(gradeNo, boardNo);
		if (boardResponse != null) {
			return ResponseEntity.ok(boardResponse);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}

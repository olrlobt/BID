package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/boards/{category}")
	public ResponseEntity<?> findBoards(@PathVariable String category, @RequestParam String keyword) {
		List<BoardResponse> boards = boardService.findBoards(1, category, keyword);
		return ResponseEntity.ok(boards);
	}


	@GetMapping("/users/{userNo}/boards")
	public ResponseEntity<?> findMyBoards(@PathVariable int userNo) {
		List<BoardResponse> boards = boardService.findMyBoards(userNo);
		return ResponseEntity.ok(boards);
	}
}

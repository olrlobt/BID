package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
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

	@GetMapping("/boards")
	public ResponseEntity<?> findBoards(@RequestParam String keyword) {
		List<BoardResponse> boards = boardService.findBoards(1, keyword);
		return ResponseEntity.ok(boards);
	}

	@GetMapping("/users/{userNo}/boards")
	public ResponseEntity<?> findMyAllBoards(@PathVariable int userNo) {
		MyBoardsResponse myAllBoards = boardService.findMyAllBoards(userNo);

		return ResponseEntity.ok(myAllBoards);
	}

	@GetMapping("/boards/{boardNo}")
	public ResponseEntity<BoardResponse> getBoardDetail(@PathVariable long boardNo) {
		BoardResponse boardResponse = coreBoardService.getBoardDetail(boardNo);
		return ResponseEntity.ok(boardResponse);
	}

	@PostMapping("/boards")
	public ResponseEntity<?> addBoard(@RequestBody BoardCreateRequest boardCreateRequest){
		boardService.addBoard(1,1,boardCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/boards/{boardNo}")
	public ResponseEntity<?> deleteBoard(@PathVariable int boardNo) {
		boardService.deleteBoard(boardNo);
		return ResponseEntity.noContent().build();
	}


}

package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.board.dto.BiddingCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.dto.ReplyCreateRequest;
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
	public ResponseEntity<?> findBoards() {
		List<BoardListResponse> boards = boardService.findBoards(1);
		return ResponseEntity.ok(boards);
	}

	@GetMapping("/users/{userNo}/boards")
	public ResponseEntity<?> findMyAllBoards(@PathVariable int userNo) {
		MyBoardsResponse myAllBoards = boardService.findMyAllBoards(userNo);
		return ResponseEntity.ok(myAllBoards);
	}

	@GetMapping("/boards/{boardNo}")
	public ResponseEntity<BoardResponse> getBoardDetail(@PathVariable long boardNo) {

		int userNo = 1;

		BoardResponse boardResponse = coreBoardService.getBoardDetail(userNo, boardNo);
		return ResponseEntity.ok(boardResponse);
	}

	@PostMapping("/boards")
	public ResponseEntity<?> addBoard(@RequestBody BoardCreateRequest boardCreateRequest) {
		boardService.addBoard(1, 1, boardCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/boards/{boardNo}")
	public ResponseEntity<?> deleteBoard(@PathVariable int boardNo) {
		boardService.deleteBoard(boardNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/boards/{boardNo}/reply")
	public ResponseEntity<?> addBoardReply(@PathVariable int boardNo,
		@RequestBody ReplyCreateRequest replyCreateRequest) {
		boardService.addBoardReply(1, boardNo, replyCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/boards/{boardNo}/reply/{replyNo}")
	public ResponseEntity<?> modifyBoardReply(@PathVariable int boardNo,
		@PathVariable int replyNo,
		@RequestBody ReplyCreateRequest replyCreateRequest) {
		boardService.modifyBoardReply(1, replyNo, replyCreateRequest);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/boards/{boardNo}/reply/{replyNo}")
	public ResponseEntity<?> deleteBoardReply(@PathVariable int boardNo,
		@PathVariable int replyNo) {
		boardService.deleteBoardReply(1, replyNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/boards/{boardNo}/bid")
	public ResponseEntity<?> bidBoard(@PathVariable long boardNo,
		@RequestBody BiddingCreateRequest biddingCreateRequest) {

		int userNo = 1;
		int gradeNo = 1;
		boardService.bidBoard(biddingCreateRequest, boardNo, gradeNo, userNo);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/boards/{boardNo}/bid")
	public ResponseEntity<?> rebidBoard(@PathVariable long boardNo,
		@RequestBody BiddingCreateRequest biddingCreateRequest) {

		int userNo = 1;
		int gradeNo = 1;
		boardService.rebidBoard(biddingCreateRequest, boardNo, gradeNo, userNo);
		return ResponseEntity.noContent().build();
	}
}

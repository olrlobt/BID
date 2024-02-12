package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.dto.BiddingCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.BoardModifyRequest;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.dto.ReplyCreateRequest;
import com.ssafy.bid.domain.board.service.BoardService;
import com.ssafy.bid.domain.board.service.CoreBoardScheduleService;
import com.ssafy.bid.domain.board.service.CoreBoardService;
import com.ssafy.bid.domain.gradeperiod.service.CoreGradePeriodService;
import com.ssafy.bid.domain.user.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BoardApi {

	private final BoardService boardService;
	private final CoreBoardService coreBoardService;
	private final CoreGradePeriodService coreGradePeriodService;
	private final CoreBoardScheduleService coreBoardScheduleService;

	@GetMapping("/boards")
	public ResponseEntity<?> findBoards(@AuthenticationPrincipal CustomUserDetails userDetails) {
		int gradeNo = userDetails.getUserInfo().getGradeNo();
		List<BoardListResponse> boards = boardService.findBoards(gradeNo);
		return ResponseEntity.ok(boards);
	}

	@GetMapping("/users/{userNo}/boards")
	public ResponseEntity<?> findAllBoardsByUserNo(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int userNo) {
		int gradeNo = userDetails.getUserInfo().getGradeNo();
		MyBoardsResponse myAllBoards = boardService.findAllBoardsByUserNo(userNo, gradeNo);
		return ResponseEntity.ok(myAllBoards);
	}

	@GetMapping("/boards/{boardNo}")
	public ResponseEntity<BoardResponse> getBoardDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable long boardNo) {

		int userNo = userDetails.getUserInfo().getNo();
		int gradeNo = userDetails.getUserInfo().getGradeNo();
		BoardResponse boardResponse = coreBoardService.getBoardDetail(userNo, boardNo, gradeNo);
		return ResponseEntity.ok(boardResponse);
	}

	@PostMapping("/boards")
	public ResponseEntity<?> addBoard(@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestBody BoardCreateRequest boardCreateRequest) {
		int userNo = userDetails.getUserInfo().getNo();
		int gradeNo = userDetails.getUserInfo().getGradeNo();
		Board board = coreBoardService.addBoard(userNo, gradeNo, boardCreateRequest);
		coreBoardScheduleService.registerBoardTask(board);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PatchMapping("/boards/{boardNo}")
	public ResponseEntity<?> modifyBoard(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int boardNo,
		@RequestBody BoardModifyRequest boardModifyRequest) {
		int userNo = userDetails.getUserInfo().getNo();
		return ResponseEntity.ok(boardService.modifyBoard(boardNo, boardModifyRequest, userNo));
	}

	@DeleteMapping("/boards/{boardNo}")
	public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int boardNo) {
		int userNo = userDetails.getUserInfo().getNo();
		boardService.deleteBoard(boardNo, userNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/boards/{boardNo}/reply")
	public ResponseEntity<?> addBoardReply(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int boardNo,
		@RequestBody ReplyCreateRequest replyCreateRequest) {
		int userNo = userDetails.getUserInfo().getNo();
		int gradeNo = userDetails.getUserInfo().getGradeNo();

		boardService.addBoardReply(userNo, gradeNo, boardNo, replyCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	// @PatchMapping("/boards/{boardNo}/reply/{replyNo}")
	// public ResponseEntity<?> modifyBoardReply(@PathVariable int boardNo,
	// 	@PathVariable int replyNo,
	// 	@RequestBody ReplyCreateRequest replyCreateRequest) {
	// 	boardService.modifyBoardReply(1, replyNo, replyCreateRequest);
	// 	return ResponseEntity.noContent().build();
	// }

	@DeleteMapping("/boards/{boardNo}/reply/{replyNo}")
	public ResponseEntity<?> deleteBoardReply(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int boardNo,
		@PathVariable int replyNo) {
		int userNo = userDetails.getUserInfo().getNo();
		boardService.deleteBoardReply(userNo, boardNo, replyNo);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/boards/{boardNo}/bid")
	public ResponseEntity<?> bidBoard(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable long boardNo,
		@RequestBody BiddingCreateRequest biddingCreateRequest) {

		int userNo = userDetails.getUserInfo().getNo();
		int gradeNo = userDetails.getUserInfo().getGradeNo();
		HttpStatus httpStatus = boardService.bidBoard(biddingCreateRequest, boardNo, gradeNo, userNo);
		return ResponseEntity.status(httpStatus).build();
	}
}

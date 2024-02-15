package com.ssafy.bid.domain.board.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.BoardResponse;
import com.ssafy.bid.domain.board.service.BoardService;
import com.ssafy.bid.domain.board.service.CoreBoardService;
import com.ssafy.bid.domain.user.UserType;
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

	@GetMapping("/{gradeNo}/boards")
	public List<BoardListResponse> findAllStudentBoards(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo) {
		int userNo = userDetails.getUserInfo().getNo();
		int userGradeNo = userDetails.getUserInfo().getGradeNo();
		return boardService.findAllStudentBoards(gradeNo, userNo, userGradeNo);
	}

	@GetMapping("/{gradeNo}/boards/{boardNo}")
	public ResponseEntity<BoardResponse> getBoardDetail(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo, @PathVariable long boardNo) {
		int userNo = userDetails.getUserInfo().getNo();
		BoardResponse boardResponse = coreBoardService.getBoardDetail(userNo, boardNo, gradeNo);
		return ResponseEntity.ok(boardResponse);
	}

	@DeleteMapping("/{gradeNo}/boards/{boardNo}")
	public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo, @PathVariable long boardNo) {
		int userNo = userDetails.getUserInfo().getNo();
		UserType userType = userDetails.getUserInfo().getUserType();
		boardService.deleteBoard(boardNo, gradeNo, userNo, userType);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{gradeNo}/boards/{boardNo}/{replyNo}")
	public ResponseEntity<?> deleteReply(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo,
		@PathVariable long boardNo,
		@PathVariable long replyNo) {
		int userNo = userDetails.getUserInfo().getNo();
		UserType userType = userDetails.getUserInfo().getUserType();
		boardService.deleteReply(replyNo, gradeNo, userNo, userType);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{gradeNo}/boards/hold")
	public ResponseEntity<?> holdBoards(@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable int gradeNo) {
		int userNo = userDetails.getUserInfo().getNo();
		UserType userType = userDetails.getUserInfo().getUserType();
		boolean isHold = boardService.holdBoards(gradeNo, userNo, userType);
		return ResponseEntity.ok(isHold);
	}
}

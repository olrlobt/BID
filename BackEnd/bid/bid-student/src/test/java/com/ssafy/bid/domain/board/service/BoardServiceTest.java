package com.ssafy.bid.domain.board.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.Reply;
import com.ssafy.bid.domain.board.dto.BoardCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.dto.ReplyCreateRequest;
import com.ssafy.bid.domain.board.repository.BiddingRepository;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@Mock
	private BoardRepository boardRepository;

	@Mock
	private ReplyRepository replyRepository;

	@Mock
	private BiddingRepository biddingRepository;

	@InjectMocks
	private BoardService boardService;


	@Test
	void findBoardsTest() {
		int gradeNo = 1;
		List<BoardListResponse> mock = getBoardListResponses();
		when(boardRepository.findBoards(gradeNo)).thenReturn(mock);

		List<BoardListResponse> result = boardService.findBoards(gradeNo);

		assertNotNull(result);
		assertEquals(mock.size(), result.size());
	}

	@NotNull
	private static List<BoardListResponse> getBoardListResponses() {
		List<BoardListResponse> mock = Arrays.asList(
			new BoardListResponse(1, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "url", "유저", 1),
			new BoardListResponse(2, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "url", "유저", 1),
			new BoardListResponse(3, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "url", "유저", 1),
			new BoardListResponse(4, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "url", "유저", 1)
		);
		return mock;
	}

	@Test
	void findAllBoardsByUserNoTest() {
		int userNo = 1;
		List<BoardListResponse> mock = getBoardListResponses();
		when(boardRepository.findMyBoards(userNo)).thenReturn(mock);
		when(boardRepository.findMyBiddingBoards(userNo)).thenReturn(mock);

		MyBoardsResponse result = boardService.findAllBoardsByUserNo(userNo);

		assertNotNull(result);
		assertFalse(result.getMyBoards().isEmpty());
		assertFalse(result.getMyBiddingBoards().isEmpty());
	}

	@Test
	void addBoardTest() {
		int userNo = 1, gradeNo = 1;
		BoardCreateRequest request = new BoardCreateRequest("제목", "설명", Category.COUPON, "url", 100 , 1); // 적절한 요청 객체 생성
		Board board = Board.builder()
			.title("제목")
			.description("설명")
			.category(Category.COUPON)
			.goodsImgUrl("url")
			.gradeNo(1)
			.build();

		when(boardRepository.save(any(Board.class))).thenReturn(board);

		assertDoesNotThrow(() -> boardService.addBoard(userNo, gradeNo, request));
	}

	@Test
	void deleteBoardTest() {
		long boardNo = 1L;
		when(boardRepository.existsById(boardNo)).thenReturn(true);

		assertDoesNotThrow(() -> boardService.deleteBoard(boardNo));
		verify(boardRepository).deleteById(boardNo);
	}

	@Test
	void addBoardReplyTest() {
		int userNo = 1, boardNo = 1;
		ReplyCreateRequest request = new ReplyCreateRequest();

		assertDoesNotThrow(() -> boardService.addBoardReply(userNo, boardNo, request));
		verify(replyRepository).save(any(Reply.class));
	}

	@Test
	void deleteBoardReplyTest() {
		int userNo = 1, replyNo = 1;
		when(replyRepository.existsById(replyNo)).thenReturn(true);

		assertDoesNotThrow(() -> boardService.deleteBoardReply(userNo, replyNo));
		verify(replyRepository).deleteById(replyNo);
	}
}
package com.ssafy.bid.domain.board.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.Category;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

	@InjectMocks
	private BoardService boardService;
	@Mock
	private BoardRepository boardRepository;
	@Mock
	private ReplyRepository replyRepository;


	@Test
	void findAllStudentBoards_경매_진행중인_경매_조회() {
		//given
		int gradeNo = 1;
		List<BoardListResponse> mockResponse = Arrays.asList(
			new BoardListResponse(1, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "www.url", "이름", 3),
			new BoardListResponse(2, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "www.url", "이름", 3),
			new BoardListResponse(3, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "www.url", "이름", 3),
			new BoardListResponse(4, "제목", BoardStatus.PROGRESS, 0, Category.COUPON, "www.url", "이름", 3)
		);
		when(boardRepository.findAllStudentBoards(gradeNo)).thenReturn(mockResponse);

		//when
		List<BoardListResponse> result = boardService.findAllStudentBoards(gradeNo);

		//then
		assertNotNull(result);
		assertEquals(mockResponse.size(), result.size());
	}

	@Test
	void deleteBoardTest_경매삭제_정상() {
		// given
		long boardNo = 1L;
		int gradeNo = 1;
		int userNo = 1;
		when(boardRepository.existsById(boardNo)).thenReturn(true);

		// when
		boardService.deleteBoard(boardNo, gradeNo, userNo);

		// then
		verify(boardRepository, times(1)).deleteById(boardNo);
	}

	@Test
	void deleteBoardTest_경매삭제_없는_게시물() {
		// given
		long boardNo = 99L; // 가정: 존재하지 않는 게시글 번호
		int gradeNo = 1;
		int userNo = 1;
		when(boardRepository.existsById(boardNo)).thenReturn(false);

		// when
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			boardService.deleteBoard(boardNo, gradeNo, userNo);
		});
	}

	void deleteBoardTest_경매삭제_선생님_학급번호_일치확인() {
		// given

	}


	@Test
	void deleteReply_경매댓글_정상_삭제() {
		// given
		long replyNo = 1L;
		int gradeNo = 1;
		int userNo = 1;
		when(replyRepository.existsById(replyNo)).thenReturn(true);

		// when
		boardService.deleteReply(replyNo, gradeNo, userNo);

		// then
		verify(replyRepository, times(1)).deleteById(replyNo);
	}


	@Test
	void deleteReply_경매댓글_없는_댓글_삭제() {
		//given
		long replyNo = 99L;
		int gradeNo = 1;
		int userNo = 1;
		when(replyRepository.existsById(replyNo)).thenReturn(false);

		// when
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			boardService.deleteReply(replyNo, gradeNo, userNo);
		});
	}
}
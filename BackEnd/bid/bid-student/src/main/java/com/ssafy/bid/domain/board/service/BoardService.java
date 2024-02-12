package com.ssafy.bid.domain.board.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.board.Bidding;
import com.ssafy.bid.domain.board.Board;
import com.ssafy.bid.domain.board.Reply;
import com.ssafy.bid.domain.board.dto.BiddingCreateRequest;
import com.ssafy.bid.domain.board.dto.BoardListResponse;
import com.ssafy.bid.domain.board.dto.BoardModifyRequest;
import com.ssafy.bid.domain.board.dto.MyBoardsResponse;
import com.ssafy.bid.domain.board.dto.ReplyCreateRequest;
import com.ssafy.bid.domain.board.repository.BiddingRepository;
import com.ssafy.bid.domain.board.repository.BoardRepository;
import com.ssafy.bid.domain.board.repository.ReplyRepository;
import com.ssafy.bid.domain.grade.Grade;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.repository.GradeRepository;
import com.ssafy.bid.domain.user.repository.StudentRepository;
import com.ssafy.bid.global.error.exception.InvalidParameterException;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	private final BiddingRepository biddingRepository;
	private final GradeRepository gradeRepository;
	private final StudentRepository studentRepository;

	public List<BoardListResponse> findBoards(int gradeNo) {
		//학생 gradeNO과 gradeNo이 맞는지 확인
		return boardRepository.findBoards(gradeNo);
	}

	public MyBoardsResponse findAllBoardsByUserNo(int userNo) {
		//조회하는 유저가 조회된 유저의 gradeNo과 맞는지 확인

		List<BoardListResponse> myBoards = boardRepository.findMyBoards(userNo);
		List<BoardListResponse> myBiddingBoards = boardRepository.findMyBiddingBoards(userNo);

		return new MyBoardsResponse(myBoards, myBiddingBoards);
	}

	@Transactional
	public Long modifyBoard(long boardNo, BoardModifyRequest boardModifyRequest) {
		// 내 게시글이 맞는지 확인 로직 필요

		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 없습니다." + boardNo));

		return board.modify(boardModifyRequest);
	}

	@Transactional
	public void deleteBoard(long boardNo) {
		// 해당 게시글이 user의 것인지 찾는 로직 추가

		if (!boardRepository.existsById(boardNo)) {
			throw new ResourceNotFoundException("해당 게시물이 없습니다.", boardNo);
		}

		List<Bidding> allBidding = biddingRepository.findAllByBoardNo(boardNo);
		if (!allBidding.isEmpty()) {
			allBidding.forEach(bidding -> {
				Student student = studentRepository.findById(bidding.getUserNo())
					.orElseThrow(() -> new ResourceNotFoundException("해당 유저가 없습니다.", bidding.getUserNo()));

				student.addPrice(bidding.getPrice());
			});
		}

		boardRepository.deleteById(boardNo);
	}

	@Transactional
	public void addBoardReply(int userNo, int boardNo, ReplyCreateRequest replyCreateRequest) {
		// 해당 유저가 게시글 gradeNo이 맞는지 확인

		replyCreateRequest.setUserNo(userNo);
		replyCreateRequest.setBoardNo(boardNo);
		Reply reply = replyCreateRequest.toEntity();
		replyRepository.save(reply);
	}

	@Transactional
	public void modifyBoardReply(int userNo, int replyNo, ReplyCreateRequest replyCreateRequest) {
		// 댓글 수정은 사용하지 않는다.
		Reply reply = replyRepository.findById(replyNo)
			.orElseThrow();

		reply.modify(replyCreateRequest.getContent());
	}

	@Transactional
	public void deleteBoardReply(int userNo, int replyNo) {
		// 해당 유저가 댓글 작성한 user가 맞나 확인

		if (!replyRepository.existsById(replyNo)) {
			throw new ResourceNotFoundException("댓글이 없습니다.", replyNo);
		}
		replyRepository.deleteById(replyNo);
	}

	@Transactional
	public HttpStatus bidBoard(BiddingCreateRequest biddingCreateRequest, long boardNo, int gradeNo, int userNo) {

		// board가 gradeNo이 user와 gradeNo이 맞나 확인
		// user의 자산이 입찰가보다 낮은 경우 확인

		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("학급이 없습니다.", gradeNo));

		if (grade.isHold()) {
			return HttpStatus.UNAUTHORIZED;
		}

		Student student = studentRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학생이 없습니다.", userNo));
		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 게시글이 없습니다.", boardNo));

		return biddingRepository.findByUserNoAndBoardNo(userNo, boardNo).map(myBidding -> {
				if (myBidding.getPrice() >= biddingCreateRequest.getPrice()) {
					throw new InvalidParameterException("새로운 입찰가가 현재 입찰가보다 낮거나 같습니다.", myBidding.getPrice(),
						biddingCreateRequest.getPrice());
				}

				int price = biddingCreateRequest.getPrice() - myBidding.getPrice();

				if (student.getAsset() < price) {
					throw new InvalidParameterException("현재 보유 자산으로는 입찰할 수 없습니다.", price);
				}

				myBidding.rebidding(biddingCreateRequest.getPrice());
				board.addTotalPrice(price);
				student.subtractPrice(price);
				return HttpStatus.NO_CONTENT;
			}
		).orElseGet(() -> {
			biddingCreateRequest.setBoardNo(boardNo);
			biddingCreateRequest.setUserNo(userNo);
			biddingCreateRequest.setGradeNo(gradeNo);
			biddingRepository.save(biddingCreateRequest.toEntity());

			if (student.getAsset() < biddingCreateRequest.getPrice()) {
				throw new InvalidParameterException("현재 보유 자산으로는 입찰할 수 없습니다.", biddingCreateRequest.getPrice());
			}
			student.subtractPrice(biddingCreateRequest.getPrice());
			board.addTotalPrice(biddingCreateRequest.getPrice());
			board.addAttendeeCount();
			return HttpStatus.CREATED;
		});
	}

}

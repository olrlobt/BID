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
import com.ssafy.bid.domain.user.UserType;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.GradeRepository;
import com.ssafy.bid.domain.user.repository.StudentRepository;
import com.ssafy.bid.global.error.exception.AuthorizationFailedException;
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
		return boardRepository.findBoards(gradeNo);
	}

	public MyBoardsResponse findAllBoardsByUserNo(int userNo, int gradeNo) {
		Student student = studentRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("찾는 유저가 없습니다", userNo));

		if (student.getGradeNo() != gradeNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		List<BoardListResponse> myBoards = boardRepository.findMyBoards(userNo);
		List<BoardListResponse> myBiddingBoards = boardRepository.findMyBiddingBoards(userNo);

		return new MyBoardsResponse(myBoards, myBiddingBoards);
	}

	@Transactional
	public Long modifyBoard(long boardNo, BoardModifyRequest boardModifyRequest, int userNo) {

		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 없습니다." + boardNo));

		if (board.getUserNo() != userNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		return board.modify(boardModifyRequest);
	}

	@Transactional
	public void deleteBoard(long boardNo, int userNo) {

		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 없습니다." + boardNo));

		if (board.getUserNo() != userNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
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
	public void addBoardReply(int userNo, int gradeNo, long boardNo, ReplyCreateRequest replyCreateRequest) {

		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 게시물이 없습니다." + boardNo));

		if (gradeNo != board.getGradeNo()) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		replyCreateRequest.setUserNo(userNo);
		replyCreateRequest.setBoardNo(boardNo);
		Reply reply = replyCreateRequest.toEntity();
		replyRepository.save(reply);
	}

	@Transactional
	public void deleteBoardReply(int userNo, int boardNo, int replyNo) {

		Reply reply = replyRepository.findById(replyNo)
			.orElseThrow(() -> new ResourceNotFoundException("댓글이 없습니다.", replyNo));

		if (reply.getUserNo() != userNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		replyRepository.deleteById(replyNo);
	}

	@Transactional
	public HttpStatus bidBoard(BiddingCreateRequest biddingCreateRequest, long boardNo, int gradeNo, int userNo) {

		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 게시글이 없습니다.", boardNo));

		if (board.getGradeNo() != gradeNo) {
			throw new AuthorizationFailedException("권한이 없습니다.");
		}

		Grade grade = gradeRepository.findById(gradeNo)
			.orElseThrow(() -> new ResourceNotFoundException("학급이 없습니다.", gradeNo));

		if (grade.isHold()) {
			return HttpStatus.UNAUTHORIZED;
		}

		Student student = studentRepository.findById(userNo)
			.orElseThrow(() -> new ResourceNotFoundException("해당 학생이 없습니다.", userNo));

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

	@Transactional
	public void transferWinningPrice(CustomUserInfo userInfo, long boardNo) {
		if (!userInfo.getUserType().equals(UserType.STUDENT)) {
			throw new AuthorizationFailedException("알맞지 않은 권한.");
		}

		Board board = boardRepository.findById(boardNo)
			.orElseThrow(() -> new ResourceNotFoundException("찾는 유저가 없습니다", userInfo.getNo()));

		Student sender = studentRepository.findById(userInfo.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("찾는 유저가 없습니다", userInfo.getNo()));
		Student receiver = studentRepository.findById(board.getUserNo())
			.orElseThrow(() -> new ResourceNotFoundException("찾는 유저가 없습니다", userInfo.getNo()));

		sender.subtractPrice(board.getResultPrice());
		receiver.addRewardPrice(board.getResultPrice());
	}
}

package com.ssafy.bid.domain.board;

import org.hibernate.annotations.ColumnDefault;

import com.ssafy.bid.domain.board.dto.BoardModifyRequest;
import com.ssafy.bid.domain.common.BaseEntity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "board")
@Entity
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_no")
	private Long no;

	@NotNull
	private String title;

	@NotNull
	private String description;

	@NotNull
	private Integer startPrice;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BoardStatus boardStatus;

	@NotNull
	@ColumnDefault("0")
	private Integer totalPrice;

	@NotNull
	@ColumnDefault("0")
	private Integer attendeeCount;

	private Integer resultPrice;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Category category;

	@NotNull
	private String goodsImgUrl;

	/**
	 * users : board(me) = 1 : N
	 */
	@NotNull
	private Integer userNo;

	/**
	 * grade : board(me) = 1 : N
	 */
	@NotNull
	private Integer gradeNo;

	/**
	 * gradePeriod : board(me) = 1 : 1
	 */
	@NotNull
	private Integer gradePeriodNo;

	/**
	 * bidding : board(me) = 1 : N
	 */
	@Nullable
	private Long biddingNo;

	@Nullable
	private Integer subNo;

	@Builder
	private Board(String title, String description, Integer startPrice, BoardStatus boardStatus, Integer totalPrice,
		Integer attendeeCount, Category category, String goodsImgUrl, Integer userNo, Integer gradeNo,
		Integer gradePeriodNo, @org.jetbrains.annotations.Nullable Integer subNo) {
		this.title = title;
		this.description = description;
		this.startPrice = startPrice;
		this.boardStatus = boardStatus;
		this.totalPrice = totalPrice;
		this.attendeeCount = attendeeCount;
		this.category = category;
		this.goodsImgUrl = goodsImgUrl;
		this.userNo = userNo;
		this.gradeNo = gradeNo;
		this.gradePeriodNo = gradePeriodNo;
		this.subNo = subNo;
	}

	public Long modify(BoardModifyRequest boardModifyRequest) {
		this.title = boardModifyRequest.getTitle();
		this.category = boardModifyRequest.getCategory();
		this.description = boardModifyRequest.getDescription();
		return this.no;
	}

	public void complete() {
		this.boardStatus = BoardStatus.COMPLETED;
	}

	public void addTotalPrice(int price) {
		this.totalPrice += price;
	}

	public void addAttendeeCount(){
		this.attendeeCount++;
	}
}

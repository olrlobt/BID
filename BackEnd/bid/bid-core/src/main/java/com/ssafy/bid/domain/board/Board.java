package com.ssafy.bid.domain.board;

import org.hibernate.annotations.ColumnDefault;

import com.ssafy.bid.domain.common.BaseEntity;

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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	private Long biddingNo;

	@Builder
	private Board(String title, String description, Integer startPrice, BoardStatus boardStatus, Integer totalPrice,
		Integer attendeeCount, Category category, String goodsImgUrl, Integer userNo, Integer gradeNo,
		Integer gradePeriodNo) {
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
	}
}

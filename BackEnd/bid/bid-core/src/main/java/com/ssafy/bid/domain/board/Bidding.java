package com.ssafy.bid.domain.board;

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
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "bidding")
@Entity
public class Bidding extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bidding_no")
	private Long no;

	@NotNull
	private Integer price;

	@NotNull
	@Enumerated(EnumType.STRING)
	private BiddingStatus biddingStatus;

	/**
	 * users : bidding(me) = 1 : N
	 */
	@NotNull
	private Integer userNo;

	/**
	 * board : bidding(me) = 1 : N
	 */
	@NotNull
	private Long boardNo;

	/**
	 * grade : bidding(me) = 1 : N
	 */
	@NotNull
	private Integer gradeNo;

	@Builder
	private Bidding(Integer price, BiddingStatus biddingStatus, Integer userNo, Long boardNo, Integer gradeNo) {
		this.price = price;
		this.biddingStatus = biddingStatus;
		this.userNo = userNo;
		this.boardNo = boardNo;
		this.gradeNo = gradeNo;
	}

	public void rebidding(int price) {
		this.price = price;
	}

}

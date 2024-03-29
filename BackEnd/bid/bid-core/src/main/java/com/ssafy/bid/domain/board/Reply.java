package com.ssafy.bid.domain.board;

import com.ssafy.bid.domain.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "reply")
@Entity
public class Reply extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reply_no")
	private Long no;

	@NotNull
	private String content;

	/**
	 * board : reply(me) = 1 : N
	 */
	@NotNull
	private Long boardNo;

	/**
	 * users : reply(me) = 1 : N
	 */
	@NotNull
	private Integer userNo;

	@Builder
	private Reply(String content, Long boardNo, Integer userNo) {
		this.content = content;
		this.boardNo = boardNo;
		this.userNo = userNo;
	}

	public void modify(String content) {
		this.content = content;
	}
}

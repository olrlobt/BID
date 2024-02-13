package com.ssafy.bid.domain.avatar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_avatar")
@Entity
public class UserAvatar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_avatar_no")
	private Integer no;

	/**
	 * user : userAvatar(me) = 1 : N
	 */
	private Integer userNo;

	/**
	 * avatar : userAvatar(me) = 1 : N
	 */
	private Integer avatarNo;

	@Builder
	public UserAvatar(
		Integer userNo,
		Integer avatarNo
	) {
		this.userNo = userNo;
		this.avatarNo = avatarNo;
	}
}

package com.ssafy.bid.domain.avatar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAvatarsFindResponse {
	private int userAvatarNo;
	private String url;
	private int avatarNo;

	public UserAvatarsFindResponse(
		int userAvatarNo,
		String url,
		int avatarNo
	) {
		this.userAvatarNo = userAvatarNo;
		this.url = url;
		this.avatarNo = avatarNo;
	}
}

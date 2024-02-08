package com.ssafy.bid.domain.avatar.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAvatarsGetResponse {
	private int userAvatarNo;
	private String url;
	private int avatarNo;

	public UserAvatarsGetResponse(
		int userAvatarNo,
		String url,
		int avatarNo
	) {
		this.userAvatarNo = userAvatarNo;
		this.url = url;
		this.avatarNo = avatarNo;
	}
}

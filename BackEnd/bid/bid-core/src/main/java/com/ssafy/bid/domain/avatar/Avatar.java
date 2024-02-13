package com.ssafy.bid.domain.avatar;

import com.ssafy.bid.domain.avatar.dto.AvatarResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "avatar")
@Entity
public class Avatar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "avatar_no")
	private Integer no;

	private String name;

	private String url;

	private Integer price;


	public AvatarResponse toDto(){

		return AvatarResponse.builder()
			.no(this.no)
			.name(this.name)
			.url(this.url)
			.build();
	}

}

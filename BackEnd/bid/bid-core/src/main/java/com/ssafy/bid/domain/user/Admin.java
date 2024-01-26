package com.ssafy.bid.domain.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DiscriminatorValue("admin")
@Entity
public class Admin extends User {

	private String tel;
}

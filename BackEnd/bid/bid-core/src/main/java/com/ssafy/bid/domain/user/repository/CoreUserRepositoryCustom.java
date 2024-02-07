package com.ssafy.bid.domain.user.repository;

import java.util.Optional;

import com.ssafy.bid.domain.user.User;

public interface CoreUserRepositoryCustom {
	Optional<User> findById(String id);
}

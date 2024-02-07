package com.ssafy.bid.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.TokenBlacklist;

public interface CoreTokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
	Optional<TokenBlacklist> findByToken(String token);
}

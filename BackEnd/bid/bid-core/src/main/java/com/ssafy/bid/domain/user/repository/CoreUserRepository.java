package com.ssafy.bid.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.User;

public interface CoreUserRepository extends JpaRepository<User, Integer>, CoreUserRepositoryCustom {
}

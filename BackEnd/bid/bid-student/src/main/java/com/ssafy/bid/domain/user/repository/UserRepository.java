package com.ssafy.bid.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.user.User;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional<User> findUserById(String id);
}

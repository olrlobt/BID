package com.ssafy.bid.domain.user.repository;

import com.ssafy.bid.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional<User> findUserById(String id);
}

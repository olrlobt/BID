package com.ssafy.bid.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.avatar.UserAvatar;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer> {
}

package com.ssafy.bid.domain.avatar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.avatar.UserAvatar;

public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer>, UserAvatarRepositoryCustom {
}

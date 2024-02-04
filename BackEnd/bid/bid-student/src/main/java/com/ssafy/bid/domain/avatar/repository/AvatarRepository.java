package com.ssafy.bid.domain.avatar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.avatar.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
}

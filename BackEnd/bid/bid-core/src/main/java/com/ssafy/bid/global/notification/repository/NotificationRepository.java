package com.ssafy.bid.global.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.bid.domain.notification.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}

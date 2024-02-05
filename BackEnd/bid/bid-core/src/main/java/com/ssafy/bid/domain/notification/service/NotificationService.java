package com.ssafy.bid.domain.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.bid.domain.notification.dto.NotificationRequest;

public interface NotificationService {
	SseEmitter subscribe(int userNo, String lastEventId);

	void send(NotificationRequest notificationRequest);
}

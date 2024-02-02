package com.ssafy.bid.global.notification.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.bid.global.notification.dto.NotificationRequest;

public interface NotificationService {
	SseEmitter subscribe(int userNo, String lastEventId);

	void send(NotificationRequest notificationRequest);
}

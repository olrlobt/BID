package com.ssafy.bid.domain.notification.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.bid.domain.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class NotificationApi {

	private static final String LAST_EVENT_ID_HEADER = "Last-Event-ID";
	private final NotificationService notificationService;

	@GetMapping(value = "/notification/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public SseEmitter subscribe(
		@RequestHeader(value = LAST_EVENT_ID_HEADER, required = false, defaultValue = "") String lastEventId
	) {
		int userNo = 2; // TODO: SecurityUser 연결
		return notificationService.subscribe(userNo, lastEventId);
	}
}

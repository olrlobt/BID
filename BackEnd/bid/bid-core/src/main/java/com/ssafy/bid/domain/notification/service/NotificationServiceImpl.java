package com.ssafy.bid.domain.notification.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.bid.domain.notification.Notification;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.repository.NotificationRepository;
import com.ssafy.bid.domain.notification.repository.SseEmitterRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

	private static final String TITLE = "title";
	private static final String CONTENT = "content";
	private static final String SSE = "sse";
	private final SseEmitterRepository sseEmitterRepository;
	private final NotificationRepository notificationRepository;

	@Override
	public SseEmitter subscribe(int userNo, String lastEventId) {
		String id = makeId(userNo);

		if (sseEmitterRepository.findAllSseEmittersByUserNo(userNo) != null) {
			sseEmitterRepository.deleteAllSseEmittersByUserNo(userNo);
		}
		SseEmitter sseEmitter = sseEmitterRepository.saveSseEmitter(id, new SseEmitter(Long.MAX_VALUE));

		// 오류 종류별 구독 취소 처리
		sseEmitter.onCompletion(() -> sseEmitterRepository.deleteSseEmitter(id)); // 네트워크 오류
		sseEmitter.onTimeout(() -> sseEmitterRepository.deleteSseEmitter(id)); // 시간 초과
		sseEmitter.onError((e) -> sseEmitterRepository.deleteSseEmitter(id)); // 오류

		// 503 에러 방지용 더미 이벤트 전송
		Object data = makeData("EventStream Created.", "[userNo=" + userNo + "]");
		sendNotification(sseEmitter, id, data);

		// 클라이언트가 미수신한 Event 목록이 존재할 경우 전송하여 Event 유실을 예방
		if (hasLostData(lastEventId)) {
			sendLostData(lastEventId, userNo, id, sseEmitter);
		}

		return sseEmitter;
	}

	@Override
	public void send(NotificationRequest notificationRequest) {
		String id = makeId(notificationRequest.getReceiverNo());
		Notification notification = notificationRequest.toEntity(id);

		// 로그인 한 유저의 SseEmotter 모두 가져오기
		Map<String, SseEmitter> sseEmitters
			= sseEmitterRepository.findAllSseEmittersByUserNo(notificationRequest.getReceiverNo());

		sseEmitters.forEach(
			(key, sseEmitter) -> {
				// 데이터 캐시 저장(유실된 데이터 처리하기 위함)
				sseEmitterRepository.saveEventCache(key, notification);
				// 데이터 전송
				sendNotificationToClient(sseEmitter, key, notification);
				notificationRepository.save(notification);
			}
		);
	}

	private String makeId(int userNo) {
		//Last-Event-ID의 값을 이용하여 유실된 데이터를 찾는데 필요한 시점을 파악하기 위한 형태
		return userNo + "_" + System.currentTimeMillis();
	}

	private Object makeData(String title, String content) {
		return Map.of(TITLE, title, CONTENT, content);
	}

	private void sendNotification(SseEmitter sseEmitter, String id, Object data) {
		try {
			sseEmitter.send(
				SseEmitter.event()
					.id(id)
					.name(SSE)
					.data(data, MediaType.APPLICATION_JSON)
			);
		} catch (IOException e) {
			sseEmitterRepository.deleteSseEmitter(id);
			sseEmitter.completeWithError(e);
		}
	}

	private void sendNotificationToClient(SseEmitter sseEmitter, String id, Object data) {
		try {
			sseEmitter.send(
				SseEmitter.event()
					.id(id)
					.name(SSE)
					.data(data, MediaType.APPLICATION_JSON)
					.reconnectTime(0)
			);

			sseEmitter.complete();
			sseEmitterRepository.deleteSseEmitter(id);

		} catch (IOException e) {
			sseEmitterRepository.deleteSseEmitter(id);
			sseEmitter.completeWithError(e);
		}
	}

	private boolean hasLostData(String lastEventId) {
		//Last-Event-Id의 존재 여부 boolean 값
		return !lastEventId.isEmpty();
	}

	private void sendLostData(String lastEventId, int userNo, String id, SseEmitter sseEmitter) {
		Map<String, Object> eventCaches = sseEmitterRepository.findAllEventCachesByUserNo(userNo);
		eventCaches.entrySet().stream()
			.filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
			.forEach(entry -> sendNotification(sseEmitter, id, entry.getValue()));
	}
}

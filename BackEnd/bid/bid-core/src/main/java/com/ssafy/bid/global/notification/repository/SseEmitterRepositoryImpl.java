package com.ssafy.bid.global.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepositoryImpl implements SseEmitterRepository {

	private final Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();
	private final Map<String, Object> eventCaches = new ConcurrentHashMap<>();

	@Override
	public SseEmitter saveSseEmitter(String sseEmitterId, SseEmitter sseEmitter) {
		sseEmitters.put(sseEmitterId, sseEmitter);
		return sseEmitter;
	}

	@Override
	public void saveEventCache(String eventCacheId, Object event) {
		eventCaches.put(eventCacheId, event);
	}

	@Override
	public Map<String, SseEmitter> findAllSseEmittersByUserNo(int userNo) {
		return sseEmitters.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(String.valueOf(userNo)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public Map<String, Object> findAllEventCachesByUserNo(int userNo) {
		return eventCaches.entrySet().stream()
			.filter(entry -> entry.getKey().startsWith(String.valueOf(userNo)))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	@Override
	public void deleteSseEmitter(String sseEmitterId) {
		sseEmitters.remove(sseEmitterId);
	}

	@Override
	public void deleteEventCache(String eventCacheId) {
		eventCaches.remove(eventCacheId);
	}

	@Override
	public void deleteAllSseEmittersByUserNo(int userNo) {
		sseEmitters.forEach((key, sseEmitter) -> {
				if (key.startsWith(String.valueOf(userNo))) {
					sseEmitters.remove(key);
				}
			}
		);
	}

	@Override
	public void deleteAllEventCachesByUserNo(int userNo) {
		eventCaches.forEach((key, sseEmitter) -> {
				if (key.startsWith(String.valueOf(userNo))) {
					sseEmitters.remove(key);
				}
			}
		);
	}
}

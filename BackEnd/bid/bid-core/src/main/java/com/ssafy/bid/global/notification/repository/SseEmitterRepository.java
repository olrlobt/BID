package com.ssafy.bid.global.notification.repository;

import java.util.Map;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterRepository {
	SseEmitter saveSseEmitter(String sseEmitterId, SseEmitter sseEmitter);

	void saveEventCache(String eventCacheId, Object event);

	Map<String, SseEmitter> findAllSseEmittersByUserNo(int userNo);

	Map<String, Object> findAllEventCachesByUserNo(int userNo);

	void deleteSseEmitter(String sseEmitterId);

	void deleteEventCache(String eventCacheId);

	void deleteAllSseEmittersByUserNo(int userNo);

	void deleteAllEventCachesByUserNo(int userNo);
}

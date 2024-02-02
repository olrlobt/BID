package com.ssafy.bid.global.notification.dto;

import com.ssafy.bid.domain.notification.Notification;
import com.ssafy.bid.domain.notification.NotificationType;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationRequest {
	private String id;
	private int receiverNo;
	private String title;
	private String content;
	private NotificationType notificationType;

	@Builder
	public NotificationRequest(
		int receiverNo,
		String title,
		String content,
		NotificationType notificationType
	) {
		this.receiverNo = receiverNo;
		this.title = title;
		this.content = content;
		this.notificationType = notificationType;
	}

	public Notification toEntity(String id) {
		return Notification.builder()
			.id(id)
			.receiverNo(receiverNo)
			.title(title)
			.content(content)
			.notificationType(notificationType)
			.build();
	}
}

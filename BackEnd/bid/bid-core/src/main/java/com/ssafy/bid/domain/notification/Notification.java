package com.ssafy.bid.domain.notification;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Table(name = "notification")
@Entity
public class Notification {
	@Id
	@Column(name = "notification_id")
	private String id;

	private Integer receiverNo;

	private String title;

	private String content;

	private NotificationType notificationType;

	private Boolean isRead;

	private LocalDateTime createdAt;

	@Builder
	public Notification(
		String id,
		Integer receiverNo,
		String title,
		String content,
		NotificationType notificationType
	) {
		this.id = id;
		this.receiverNo = receiverNo;
		this.title = title;
		this.content = content;
		this.notificationType = notificationType;
		this.isRead = false;
		this.createdAt = LocalDateTime.now();
	}
}

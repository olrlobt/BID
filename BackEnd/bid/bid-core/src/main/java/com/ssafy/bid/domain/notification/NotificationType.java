package com.ssafy.bid.domain.notification;

public enum NotificationType {
	REWARD("리워드"),
	COUPON_REQUEST("쿠폰 사용 요청"),
	COUPON_APPROVE("쿠폰 승인 여부"),
	BIDDING_WINNER("자기 입찰 경매 낙찰"),
	BIDDING_UPLOADER("자기 등록 경매 낙찰"),
	SAVING_TRANSFER("적금 이체"),
	SAVING_EXPIRE("적금 만기");

	private final String notificationType;

	NotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
}

package com.ssafy.bid.domain.notification;

public enum NotificationType {
	REWARD("리워드"),
	COUPON_REQUEST("쿠폰 사용 요청"),
	COUPON_APPROVE("쿠폰 승인 여부"),
	BIDDING_FAILED("입찰한 경매 유찰"),
	BIDDING_WINNING("입찰한 경매 낙찰"),
	BIDDING_UPLOADER("자기 등록 경매 낙찰"),
	SAVING_TRANSFER("적금 이체"),
	SAVING_LACK("적금 이체 금액 부족"),
	SAVING_EXPIRE("적금 만기"),
	ETC("기타");

	private final String notificationType;

	NotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
}

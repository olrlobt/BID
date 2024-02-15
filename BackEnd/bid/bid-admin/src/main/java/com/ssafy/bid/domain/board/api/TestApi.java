package com.ssafy.bid.domain.board.api;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.service.CoreSavingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TestApi {

	private final NotificationService notificationService;
	private final CoreSavingService coreSavingService;

	@GetMapping("/test/one/{userNo}")
	public void sendSavingTransferAlert(@PathVariable int userNo) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("오늘 ").append(LocalTime.now().format(formatter)).append("시에 적금 금액이 빠져나가요");
		NotificationRequest notificationRequest = NotificationRequest.builder()
			.receiverNo(userNo)
			.title(LocalTime.now().format(formatter))
			.content(sb.toString())
			.notificationType(NotificationType.SAVING_TRANSFER)
			.build();
		notificationService.send(notificationRequest);
	}

	@GetMapping("/test/two/{userNo}")
	public void sendSavingExpireAlert(@PathVariable int userNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("적금이 만료되어 ").append("200").append("원이 입금되었어요");
		NotificationRequest notificationRequest = NotificationRequest.builder()
			.receiverNo(userNo)
			.title("200")
			.content(sb.toString())
			.notificationType(NotificationType.SAVING_EXPIRE)
			.build();
		notificationService.send(notificationRequest);
	}

	@GetMapping("/test/three/{userNo}")
	public void transferWinningPriceAlert(@PathVariable int userNo) {
		StringBuilder sb = new StringBuilder();
		sb.append("곰돌이 인형").append(" 경매가 끝났어요! 친구에게 전달해주세요.");
		NotificationRequest notificationRequest = NotificationRequest.builder()
			.receiverNo(userNo)
			.title("곰돌이 인형")
			.content(sb.toString())
			.notificationType(NotificationType.BIDDING_UPLOADER)
			.build();
		notificationService.send(notificationRequest);
	}

	@GetMapping("/test/four/{userNo}")
	public void boardLose(@PathVariable int userNo) {
		StringBuilder forWinner = new StringBuilder();
		forWinner.append("곰돌이 인형").append(" 경매에 낙찰되었어요");
		for (int i = 1; i < 6; i++) {
			forWinner.append(i+1).append("등 입찰가 : ").append(300 - i * 10).append("\n");
		}
		notificationService.send(NotificationRequest.builder()
			.title("1")
			.content(forWinner.toString())
			.receiverNo(userNo)
			.notificationType(NotificationType.BIDDING_WINNING)
			.subNo(1)
			.build());
	}

	@GetMapping("/test/five/{userNo}")
	public void boardWin(@PathVariable int userNo) {
		StringBuilder forLoser = new StringBuilder();
		forLoser.append("곰돌이 인형").append(" 경매에 유찰되었어요");
		for (int i = 0; i < 6; i++) {
			if (i >= 5) {
				break;
			}
			forLoser.append(i+1).append("등 입찰가 : ").append(300 - i * 10).append("\n");
		}
		notificationService.send(NotificationRequest.builder()
			.title("곰돌이 인형")
			.content(forLoser.toString())
			.receiverNo(userNo)
			.notificationType(NotificationType.BIDDING_FAILED)
			.build());
	}
}

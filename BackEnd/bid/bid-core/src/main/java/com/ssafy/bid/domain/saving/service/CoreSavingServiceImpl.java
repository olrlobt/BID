package com.ssafy.bid.domain.saving.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferRequest;
import com.ssafy.bid.domain.saving.repository.CoreUserSavingRepository;
import com.ssafy.bid.domain.user.Account;
import com.ssafy.bid.domain.user.AccountType;
import com.ssafy.bid.domain.user.DealType;
import com.ssafy.bid.domain.user.repository.AccountRepository;
import com.ssafy.bid.domain.user.repository.CoreUserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreSavingServiceImpl implements CoreSavingService {

	private final CoreUserSavingRepository coreUserSavingRepository;
	private final CoreUserRepository coreUserRepository;
	private final NotificationService notificationService;
	private final AccountRepository accountRepository;

	@Override
	@Transactional
	public List<SavingExpireAlertRequest> expire() {
		List<Integer> userSavings = new ArrayList<>();
		List<SavingExpireAlertRequest> savingExpireAlertRequests = new ArrayList<>();

		coreUserSavingRepository.findAllSavingExpireInfos().stream()
			.filter(savingExpireRequest -> isExpireTarget(savingExpireRequest.getUserSavingEndPeriod()))
			.forEach(savingExpireRequest -> {
				userSavings.add(savingExpireRequest.getUserSavingNo());
				savingExpireAlertRequests.add(createSavingTransferAlertRequest(savingExpireRequest));
				savingExpireRequest.getStudent().addPrice(savingExpireRequest.getCurrentPrice());
			});

		coreUserSavingRepository.deleteAllById(userSavings);
		return savingExpireAlertRequests;
	}

	private boolean isExpireTarget(LocalDate userSavingEndPeriod) {
		return userSavingEndPeriod.equals(LocalDate.now());
	}

	private SavingExpireAlertRequest createSavingTransferAlertRequest(SavingExpireRequest savingExpireRequest) {
		return SavingExpireAlertRequest.builder()
			.price(savingExpireRequest.getCurrentPrice())
			.endDate(savingExpireRequest.getUserSavingEndPeriod())
			.userNo(savingExpireRequest.getStudent().getNo())
			.build();
	}

	@Override
	public List<SavingTransferAlertRequest> findAllSavingTransferInfos() {
		return coreUserSavingRepository.findAllSavingTransferInfos();
	}

	@Override
	@Transactional
	public void transfer() {
		List<Integer> targetUserNos = coreUserSavingRepository.findAll().stream()
			.filter(this::isTransferTarget)
			.map(UserSaving::getUserNo)
			.toList();

		List<Account> accounts = new ArrayList<>();
		coreUserRepository.findAllByIds(targetUserNos).stream()
			.filter(this::isStudentAssetEnough)
			.forEach(request -> {
				request.getStudent().subtractPrice(request.getPrice());
				Account account = Account.builder()
					.accountType(AccountType.EXPENDITURE)
					.price(request.getPrice())
					.content("적금 이체.")
					.dealType(DealType.SAVING)
					.userNo(request.getStudent().getNo())
					.gradeNo(request.getStudent().getGradeNo())
					.build();
				accounts.add(account);
			});
		accountRepository.saveAll(accounts);
	}

	private boolean isTransferTarget(UserSaving userSaving) {
		if (userSaving.getSavingNo().equals(1)) {
			return true;
		}

		Period period = Period.between(userSaving.getStartPeriod().toLocalDate(), LocalDateTime.now().toLocalDate());
		return period.getDays() % 7 == 0;
	}

	private boolean isStudentAssetEnough(SavingTransferRequest savingTransferRequest) {
		int diff = savingTransferRequest.getStudent().getAsset() - savingTransferRequest.getPrice();
		if (diff < 0) {
			NotificationRequest notificationRequest = createRequest(savingTransferRequest.getStudent().getNo(), diff);
			notificationService.send(notificationRequest);
		}
		return diff >= 0;
	}

	private NotificationRequest createRequest(int userNo, int diff) {
		return NotificationRequest.builder()
			.receiverNo(userNo)
			.title("적금 이체 금액 부족 알림")
			.content(String.valueOf(diff))
			.notificationType(NotificationType.SAVING_LACK)
			.build();
	}
}

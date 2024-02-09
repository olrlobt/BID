package com.ssafy.bid.domain.saving.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.repository.CoreUserSavingRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreSavingServiceImpl implements CoreSavingService {

	private final CoreUserSavingRepository coreUserSavingRepository;

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
				savingExpireRequest.getStudent().addSavingPrice(savingExpireRequest.getCurrentPrice());
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
}

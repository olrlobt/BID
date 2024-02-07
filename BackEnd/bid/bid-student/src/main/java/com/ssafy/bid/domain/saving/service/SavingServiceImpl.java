package com.ssafy.bid.domain.saving.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.Saving;
import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingExpireRequest;
import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferRequest;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.domain.saving.repository.UserSavingRepository;
import com.ssafy.bid.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SavingServiceImpl implements SavingService {

	private final SavingRepository savingRepository;
	private final UserSavingRepository userSavingRepository;
	private final UserRepository userRepository;

	@Override
	@Transactional
	public void saveSaving(int userNo, SavingRequest savingRequest) {
		Saving saving = savingRepository.findById(savingRequest.getNo())
			.orElseThrow(() -> new IllegalArgumentException("적금 없음"));//TODO: 커스텀 예외처리
		UserSaving userSaving = savingRequest.toEntity(saving, userNo);
		userSavingRepository.save(userSaving);
	}

	@Override
	@Transactional
	public void deleteSaving(int userNo, int savingNo) {
		userSavingRepository.deleteByUserNoAndSavingNo(userNo, savingNo);
	}

	@Override
	public List<SavingTransferAlertRequest> findAllSavingTransferInfos() {
		return userSavingRepository.findAllSavingTransferInfos();
	}

	@Override
	@Transactional
	public void transfer() {
		List<Integer> targetUserNos = userSavingRepository.findAll().stream()
			.filter(this::isTransferTarget)
			.map(UserSaving::getUserNo)
			.toList();

		userRepository.findAllByIds(targetUserNos).stream()
			.filter(this::isStudentAssetLack)
			.forEach(request -> request.getStudent().subtractSavingPrice(request.getPrice()));
	}

	@Override
	@Transactional
	public List<SavingExpireAlertRequest> expire() {
		List<Integer> userSavings = new ArrayList<>();
		List<SavingExpireAlertRequest> savingExpireAlertRequests = new ArrayList<>();

		userSavingRepository.findAllSavingExpireInfos().stream()
			.filter(savingExpireRequest -> isExpireTarget(savingExpireRequest.getUserSavingEndPeriod()))
			.forEach(savingExpireRequest -> {
				userSavings.add(savingExpireRequest.getUserSavingNo());
				savingExpireAlertRequests.add(createSavingTransferAlertRequest(savingExpireRequest));
				savingExpireRequest.getStudent().addSavingPrice(savingExpireRequest.getCurrentPrice());
			});

		userSavingRepository.deleteAllById(userSavings);
		return savingExpireAlertRequests;
	}

	private boolean isTransferTarget(UserSaving userSaving) {
		if (userSaving.getSavingNo().equals(1)) {
			return true;
		}

		Period period = Period.between(userSaving.getStartPeriod().toLocalDate(), LocalDateTime.now().toLocalDate());
		return period.getDays() % 7 == 0;
	}

	private boolean isStudentAssetLack(SavingTransferRequest savingTransferRequest) {
		// TODO: 잔액부족 실시간 알림??
		return savingTransferRequest.getStudent().getAsset() - savingTransferRequest.getPrice() >= 0;
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
}

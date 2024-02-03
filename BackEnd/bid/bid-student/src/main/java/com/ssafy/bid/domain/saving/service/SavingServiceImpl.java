package com.ssafy.bid.domain.saving.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.saving.UserSaving;
import com.ssafy.bid.domain.saving.dto.SavingExpireResponse;
import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;
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
	public List<SavingsResponse> findSavings(int gradeNo) {
		return savingRepository.findSavings(gradeNo);
	}

	@Override
	@Transactional
	public void saveSaving(int userNo, SavingRequest savingRequest) {
		UserSaving userSaving = savingRequest.toEntity(userNo);
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
			.filter(this::isTarget)
			.map(UserSaving::getUserNo)
			.toList();

		userRepository.findAllByIds(targetUserNos).stream()
			.filter(this::isLack)
			.forEach(request -> request.getStudent().minusSavingPrice(request.getPrice()));
	}

	@Override
	@Transactional
	public List<SavingExpireResponse> expire() {
		List<Integer> userSavings = new ArrayList<>();
		List<SavingExpireResponse> savingExpireResponses = new ArrayList<>();

		userSavingRepository.findSavingExpireInfos().stream()
			.filter(info -> info.getUserSaving().getEndPeriod().toLocalDate().equals(LocalDate.now()))
			.forEach(info -> {
				userSavings.add(info.getUserSaving().getNo());
				savingExpireResponses.add(
					SavingExpireResponse.builder()
						.price(info.getUserSaving().getResultPrice())
						.endDate(info.getUserSaving().getEndPeriod().toLocalDate())
						.userNo(info.getStudent().getNo())
						.build()
				);
				info.getStudent().addSavingPrice(info.getUserSaving().getResultPrice());
			});

		userSavingRepository.deleteAllById(userSavings);
		return savingExpireResponses;
	}

	private boolean isTarget(UserSaving userSaving) {
		if (userSaving.getSavingNo().equals(1)) {
			return true;
		}

		Period period = Period.between(userSaving.getStartPeriod().toLocalDate(), LocalDateTime.now().toLocalDate());
		return period.getDays() % 7 == 0;
	}

	private boolean isLack(SavingTransferRequest savingTransferRequest) {
		// TODO: 잔액부족 실시간 알림??
		return savingTransferRequest.getStudent().getAsset() - savingTransferRequest.getPrice() >= 0;
	}
}

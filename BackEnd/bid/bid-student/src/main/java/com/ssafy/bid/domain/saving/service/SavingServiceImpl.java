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
import com.ssafy.bid.domain.saving.dto.SavingSaveRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.dto.SavingTransferRequest;
import com.ssafy.bid.domain.saving.repository.SavingRepository;
import com.ssafy.bid.domain.saving.repository.UserSavingRepository;
import com.ssafy.bid.domain.user.dto.CustomUserInfo;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

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
	public void saveSavings(CustomUserInfo userInfo, SavingSaveRequest savingSaveRequest) {
		// 적금 조회 및 파라미터 검증
		Saving saving = savingRepository.findById(savingSaveRequest.getNo())
			.orElseThrow(() -> new ResourceNotFoundException("회원적금등록-적금PK", savingSaveRequest.getNo()));

		// DTO -> Entity 변환
		UserSaving userSaving = savingSaveRequest.toEntity(saving, userInfo);

		// Entity DB에 저장
		userSavingRepository.save(userSaving);
	}

	@Override
	@Transactional
	public void deleteSavings(int userNo, int savingNo) {
		// 해당하는 회원적금 존재여부 조회
		boolean exists = userSavingRepository.existsByUserNoAndSavingNo(userNo, savingNo);

		// 파라미터 검증
		if (!exists) {
			throw new ResourceNotFoundException("회원적금삭제-회원PK", userNo);
		}

		// Entity DB에서 삭제
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

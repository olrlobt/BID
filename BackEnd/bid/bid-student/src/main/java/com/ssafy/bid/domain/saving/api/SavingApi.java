package com.ssafy.bid.domain.saving.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;
import com.ssafy.bid.domain.saving.service.SavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SavingApi {

	private final SavingService savingService;

	@GetMapping("/savings")
	public List<SavingsResponse> findSavings() {
		//TODO: SecurityUser 설정 후, @AuthenticationPrincipal 로 로그인 정보 내에서 학급pk 가져와서 파라미터로 넘겨야 함
		return savingService.findSavings(1);
	}

	@PostMapping("/savings")
	public ResponseEntity<Void> saveSavings(SavingRequest savingRequest) {
		//TODO: SecurityUser 설정 후, @AuthenticationPrincipal 로 로그인 정보 내에서 학급pk 가져와서 파라미터로 넘겨야 함
		savingService.saveSaving(2, savingRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}

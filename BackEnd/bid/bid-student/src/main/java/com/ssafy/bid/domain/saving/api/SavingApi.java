package com.ssafy.bid.domain.saving.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.saving.dto.SavingRequest;
import com.ssafy.bid.domain.saving.dto.SavingsResponse;
import com.ssafy.bid.domain.saving.service.SavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SavingApi {

	private final SavingService savingService;

	@GetMapping("/savings")
	public List<SavingsResponse> findSavings() {
		//TODO: SecurityUser 설정 후, @AuthenticationPrincipal 로 로그인 정보 내에서 학급pk 가져와서 파라미터로 넘겨야 함
		return savingService.findSavings(1);
	}

	@PostMapping("/savings")
	public ResponseEntity<Void> saveSaving(@RequestBody SavingRequest savingRequest) {
		//TODO: SecurityUser 설정 후, @AuthenticationPrincipal 로 로그인 정보 내에서 학급pk 가져와서 파라미터로 넘겨야 함
		savingService.saveSaving(2, savingRequest);
		return ResponseEntity.status(CREATED).build();
	}

	@DeleteMapping("/savings/{savingNo}")
	public ResponseEntity<Void> deleteSaving(@PathVariable int savingNo) {
		//TODO: SecurityUser 설정 후, @AuthenticationPrincipal 로 로그인 정보 내에서 학급pk 가져와서 파라미터로 넘겨야 함
		savingService.deleteSaving(2, savingNo);
		return ResponseEntity.status(NO_CONTENT).build();
	}
}

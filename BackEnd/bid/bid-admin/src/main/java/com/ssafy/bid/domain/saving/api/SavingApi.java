package com.ssafy.bid.domain.saving.api;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bid.domain.saving.dto.SavingListGetResponse;
import com.ssafy.bid.domain.saving.dto.SavingListUpdateRequest;
import com.ssafy.bid.domain.saving.service.SavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SavingApi {

	private final SavingService savingService;

	@GetMapping("/{gradeNo}/savings")
	public ResponseEntity<List<SavingListGetResponse>> findSavings(@PathVariable int gradeNo) {
		List<SavingListGetResponse> responses = savingService.getAllSaving(gradeNo);
		return ResponseEntity.status(OK).body(responses);
	}

	@PatchMapping("/{gradeNo}/savings")
	public ResponseEntity<?> updateSaving(@PathVariable int gradeNo,
		@RequestBody SavingListUpdateRequest savingListUpdateRequest) {
		savingService.updateSaving(gradeNo, savingListUpdateRequest);
		return ResponseEntity.status(OK).build();
	}
}

package com.ssafy.bid.domain.board.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ssafy.bid.domain.board.BoardStatus;
import com.ssafy.bid.domain.board.repository.CoreBoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoreBoardScheduleInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final CoreBoardRepository coreBoardRepository;
	private final CoreBoardScheduleService coreBoardScheduleService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		coreBoardRepository.findAllByBoardStatus(BoardStatus.PROGRESS)
			.forEach(coreBoardScheduleService::registerBoardTask);
		log.info("=========== CoreBoardScheduleInitializer =============");
	}
}

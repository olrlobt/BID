package com.ssafy.bid.configuration.scheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class BoardSchedulerConfig {

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(1000); // 동시에 실행할 스케쥴링 작업의 수 설정
		scheduler.setThreadNamePrefix("scheduled-task-");
		scheduler.initialize();
		return scheduler;
	}
}

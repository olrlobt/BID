package com.ssafy.bid.configuration.batch.notification;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SavingNotificationScheduler {

	private final JobLauncher jobLauncher;
	private final SavingNotificationJob savingNotificationJob;
	private final JobRepository jobRepository;
	private final Step step;

	@Scheduled(cron = "0 50 8 * * *")
		// 매일 8시 50분에 실행
	void notifySavingTransfer() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			savingNotificationJob.savingTransferAlertJob(jobRepository, step),
			new JobParameters()
		);
	}
}

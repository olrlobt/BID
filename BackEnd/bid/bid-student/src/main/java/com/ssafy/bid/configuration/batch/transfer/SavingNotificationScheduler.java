package com.ssafy.bid.configuration.batch.transfer;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.bid.configuration.batch.notification.SavingNotificationBatchJob;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SavingNotificationScheduler {

	private final JobLauncher jobLauncher;
	private final SavingNotificationBatchJob savingNotificationBatchJob;
	private final JobRepository jobRepository;
	@Qualifier("savingTransferAlertStep")
	private final Step savingTransferAlertStep;

	@Scheduled(cron = "0 50 8 * * *")
		// 매일 8시 50분에 실행
	void notifySavingTransfer() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			savingNotificationBatchJob.savingTransferAlertJob(jobRepository, savingTransferAlertStep),
			new JobParameters()
		);
	}
}

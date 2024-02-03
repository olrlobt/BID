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

import com.ssafy.bid.configuration.batch.notification.SavingTransferBatchJob;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SavingTransferScheduler {

	private final JobLauncher jobLauncher;
	private final SavingTransferBatchJob savingTransferBatchJob;
	private final JobRepository jobRepository;
	@Qualifier("savingTransferStep")
	private final Step savingTransferStep;

	@Scheduled(cron = "0 0 15 * * *")
	void transfer() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			savingTransferBatchJob.savingTransferJob(jobRepository, savingTransferStep),
			new JobParameters()
		);
	}
}

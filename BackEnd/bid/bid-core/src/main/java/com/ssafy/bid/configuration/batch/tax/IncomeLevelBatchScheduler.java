package com.ssafy.bid.configuration.batch.tax;

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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class IncomeLevelBatchScheduler {

	private final JobLauncher jobLauncher;
	private final IncomeLevelBatchJob incomeLevelBatchJob;
	private final JobRepository jobRepository;
	@Qualifier("incomeLevelStep")
	private final Step incomeLevelStep;

	@Scheduled(cron = "0 12 8 * * *")
	void incomeLevel() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			incomeLevelBatchJob.incomeLevelJob(jobRepository, incomeLevelStep),
			new JobParameters()
		);
	}
}

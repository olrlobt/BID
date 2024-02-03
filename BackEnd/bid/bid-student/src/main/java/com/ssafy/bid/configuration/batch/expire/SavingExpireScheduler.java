package com.ssafy.bid.configuration.batch.expire;

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
public class SavingExpireScheduler {

	private final JobLauncher jobLauncher;
	private final SavingExpireBatchJob savingExpireBatchJob;
	private final JobRepository jobRepository;
	@Qualifier("savingExpireStep")
	private final Step savingExpireStep;

	@Scheduled(cron = "0 40 8 * * *")
		// 적금 이체 알림보다 이전시간에 작동해야 함
	void expire() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			savingExpireBatchJob.savingExpireJob(jobRepository, savingExpireStep),
			new JobParameters()
		);
	}
}

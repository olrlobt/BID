package com.ssafy.bid.configuration.batch.statistics;

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
public class GradeStatisticsScheduler {

	private final JobLauncher jobLauncher;
	private final GradeStatisticsJob gradeStatisticsJob;
	private final JobRepository jobRepository;
	@Qualifier("gradeStatisticsUpdateStep")
	private final Step gradeStatisticsUpdateStep;

	@Scheduled(cron = "0 0 4 * * *")
	void update() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			gradeStatisticsJob.gradeStatisticsUpdateJob(jobRepository, gradeStatisticsUpdateStep),
			new JobParameters()
		);
	}
}

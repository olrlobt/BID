package com.ssafy.bid.configuration.batch.salary;

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
public class SalaryRecommendationBatchScheduler {

	private final JobLauncher jobLauncher;
	private final SalaryRecommendationBatchJob salaryRecommendationBatchJob;
	private final JobRepository jobRepository;
	@Qualifier("salaryRecommendationStep")
	private final Step salaryRecommendationStep;

	@Scheduled(cron = "0 50 10 * * 6")
	void salaryRecommendation() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		jobLauncher.run(
			salaryRecommendationBatchJob.salaryRecommendationJob(jobRepository, salaryRecommendationStep),
			new JobParameters()
		);
	}
}

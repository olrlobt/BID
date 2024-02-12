package com.ssafy.bid.configuration.batch.tax;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.ssafy.bid.domain.user.service.CoreUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class IncomeLevelBatchJob {

	private final CoreUserService coreUserService;

	@Bean
	public Job incomeLevelJob(JobRepository jobRepository, Step incomeLevelStep) {
		return new JobBuilder("incomeLevelJob", jobRepository)
			.start(incomeLevelStep)
			.build();
	}

	@Bean
	public Step incomeLevelStep(
		JobRepository jobRepository,
		Tasklet incomeLevelTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("incomeLevelStep", jobRepository)
			.tasklet(incomeLevelTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet incomeLevelTasklet() {
		return ((contribution, chunkContext) -> {
			coreUserService.calculateIncomeLevel();
			return RepeatStatus.FINISHED;
		});
	}
}

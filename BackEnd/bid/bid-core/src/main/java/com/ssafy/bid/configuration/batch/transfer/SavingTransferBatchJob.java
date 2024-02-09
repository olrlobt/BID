package com.ssafy.bid.configuration.batch.transfer;

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

import com.ssafy.bid.domain.saving.service.CoreSavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SavingTransferBatchJob {

	private final CoreSavingService coreSavingService;

	@Bean
	public Job savingTransferJob(JobRepository jobRepository, Step savingTransferStep) {
		return new JobBuilder("savingTransferJob", jobRepository)
			.start(savingTransferStep)
			.build();
	}

	@Bean
	public Step savingTransferStep(
		JobRepository jobRepository,
		Tasklet savingTransferTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("savingTransferStep", jobRepository)
			.tasklet(savingTransferTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet savingTransferTasklet() {
		return ((contribution, chunkContext) -> {
			coreSavingService.transfer();
			return RepeatStatus.FINISHED;
		});
	}
}

package com.ssafy.bid.configuration.batch.statistics;

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

import com.ssafy.bid.domain.grade.service.CoreGradeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class GradeStatisticsJob {

	private final CoreGradeService coreGradeService;

	@Bean
	public Job gradeStatisticsUpdateJob(JobRepository jobRepository, Step gradeStatisticsUpdateStep) {
		return new JobBuilder("gradeStatisticsUpdateJob", jobRepository)
			.start(gradeStatisticsUpdateStep)
			.build();
	}

	@Bean
	public Step gradeStatisticsUpdateStep(
		JobRepository jobRepository,
		Tasklet gradeStatisticsUpdateTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("gradeStatisticsUpdateStep", jobRepository)
			.tasklet(gradeStatisticsUpdateTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet gradeStatisticsUpdateTasklet() {
		return ((contribution, chunkContext) -> {
			coreGradeService.updateGradeStatistics();
			return RepeatStatus.FINISHED;
		});
	}
}

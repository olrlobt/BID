package com.ssafy.bid.configuration.batch.salary;

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
public class SalaryRecommendationBatchJob {

	private final CoreGradeService coreGradeService;

	@Bean
	public Job salaryRecommendationJob(JobRepository jobRepository, Step salaryRecommendationStep) {
		return new JobBuilder("salaryRecommendationJob", jobRepository)
			.start(salaryRecommendationStep)
			.build();
	}

	@Bean
	public Step salaryRecommendationStep(
		JobRepository jobRepository,
		Tasklet salaryRecommendationTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("salaryRecommendationStep", jobRepository)
			.tasklet(salaryRecommendationTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet salaryRecommendationTasklet() {
		return ((contribution, chunkContext) -> {
			coreGradeService.recommendSalary();
			return RepeatStatus.FINISHED;
		});
	}
}

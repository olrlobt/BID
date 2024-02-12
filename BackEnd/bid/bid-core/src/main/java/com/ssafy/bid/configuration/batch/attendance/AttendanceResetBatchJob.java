package com.ssafy.bid.configuration.batch.attendance;

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
public class AttendanceResetBatchJob {

	private final CoreUserService coreUserService;

	@Bean
	public Job attendanceResetJob(JobRepository jobRepository, Step attendanceResetStep) {
		return new JobBuilder("attendanceResetJob", jobRepository)
			.start(attendanceResetStep)
			.build();
	}

	@Bean
	public Step attendanceResetStep(
		JobRepository jobRepository,
		Tasklet attendanceResetTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("attendanceResetStep", jobRepository)
			.tasklet(attendanceResetTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet attendanceResetTasklet() {
		return ((contribution, chunkContext) -> {
			coreUserService.resetAttendance();
			return RepeatStatus.FINISHED;
		});
	}
}

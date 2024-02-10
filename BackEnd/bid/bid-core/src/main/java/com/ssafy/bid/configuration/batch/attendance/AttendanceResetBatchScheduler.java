package com.ssafy.bid.configuration.batch.attendance;

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
public class AttendanceResetBatchScheduler {

	private final JobLauncher jobLauncher;
	private final AttendanceResetBatchJob attendanceResetBatchJob;
	private final JobRepository jobRepository;
	@Qualifier("attendanceResetStep")
	private final Step attendanceResetStep;

	@Scheduled(cron = "0 0 0 * * 0")
	void resetAttendance() throws
		JobInstanceAlreadyCompleteException,
		JobExecutionAlreadyRunningException,
		JobParametersInvalidException,
		JobRestartException {
		System.out.println();
		jobLauncher.run(
			attendanceResetBatchJob.attendanceResetJob(jobRepository, attendanceResetStep),
			new JobParameters()
		);
	}
}

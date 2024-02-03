package com.ssafy.bid.configuration.batch.notification;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.saving.repository.UserSavingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@EnableBatchProcessing
@Configuration
public class SavingNotificationBatchJob {

	private final NotificationService notificationService;
	private final UserSavingRepository userSavingRepository;

	@Bean
	public Job savingTransferAlertJob(JobRepository jobRepository, Step savingTransferAlertStep) {
		return new JobBuilder("savingTransferAlertJob", jobRepository)
			.start(savingTransferAlertStep)
			.build();
	}

	@Bean
	public Step savingTransferAlertStep(
		JobRepository jobRepository,
		Tasklet savingTransferAlertTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("savingTransferAlertStep", jobRepository)
			.tasklet(savingTransferAlertTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet savingTransferAlertTasklet() {
		return ((contribution, chunkContext) -> {
			userSavingRepository.findSavingTransferInfos().forEach(request -> {
				NotificationRequest notificationRequest = NotificationRequest.builder()
					.receiverNo(request.getUserNo())
					.title("적금 이체 알림")
					.content(String.valueOf(request.getPrice())
						.concat(" ")
						.concat(String.valueOf(request.getTransferPeriod())))
					.notificationType(NotificationType.SAVING_TRANSFER)
					.build();
				notificationService.send(notificationRequest);
			});
			return RepeatStatus.FINISHED;
		});
	}
}

package com.ssafy.bid.configuration.batch.notification;

import java.time.format.DateTimeFormatter;

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

import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.saving.dto.SavingTransferAlertRequest;
import com.ssafy.bid.domain.saving.service.CoreSavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SavingNotificationBatchJob {

	private final NotificationService notificationService;
	private final CoreSavingService coreSavingService;

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
			coreSavingService.findAllSavingTransferInfos().forEach(transferAlertRequest -> {
				NotificationRequest notificationRequest = createRequest(transferAlertRequest);
				notificationService.send(notificationRequest);
			});
			return RepeatStatus.FINISHED;
		});
	}

	private NotificationRequest createRequest(SavingTransferAlertRequest transferAlertRequest) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
		StringBuilder sb = new StringBuilder();
		sb.append("오늘 ").append(transferAlertRequest.getTransferPeriod().format(formatter)).append("시에 적금 금액이 빠져나가요");
		return NotificationRequest.builder()
			.receiverNo(transferAlertRequest.getUserNo())
			.title(transferAlertRequest.getTransferPeriod().format(formatter))
			.content(sb.toString())
			.notificationType(NotificationType.SAVING_TRANSFER)
			.build();
	}
}

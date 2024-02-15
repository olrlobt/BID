package com.ssafy.bid.configuration.batch.expire;

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
import com.ssafy.bid.domain.saving.dto.SavingExpireAlertRequest;
import com.ssafy.bid.domain.saving.service.CoreSavingService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SavingExpireBatchJob {

	private final CoreSavingService coreSavingService;
	private final NotificationService notificationService;

	@Bean
	public Job savingExpireJob(JobRepository jobRepository, Step savingExpireStep) {
		return new JobBuilder("savingExpireJob", jobRepository)
			.start(savingExpireStep)
			.build();
	}

	@Bean
	public Step savingExpireStep(
		JobRepository jobRepository,
		Tasklet savingExpireTasklet,
		PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("savingExpireStep", jobRepository)
			.tasklet(savingExpireTasklet, platformTransactionManager)
			.allowStartIfComplete(true)
			.build();
	}

	@Bean
	public Tasklet savingExpireTasklet() {
		return ((contribution, chunkContext) -> {
			coreSavingService.expire().forEach(savingExpireAlertRequest -> {
				NotificationRequest notificationRequest = createNotificationRequest(savingExpireAlertRequest);
				notificationService.send(notificationRequest);
			});
			return RepeatStatus.FINISHED;
		});
	}

	private NotificationRequest createNotificationRequest(SavingExpireAlertRequest savingExpireAlertRequest) {
		StringBuilder sb = new StringBuilder();
		sb.append("적금이 만료되어 ").append(savingExpireAlertRequest.getPrice()).append("원이 입금되었어요");
		return NotificationRequest.builder()
			.receiverNo(savingExpireAlertRequest.getUserNo())
			.title(String.valueOf(savingExpireAlertRequest.getPrice()))
			.content(sb.toString())
			.notificationType(NotificationType.SAVING_EXPIRE)
			.build();
	}
}

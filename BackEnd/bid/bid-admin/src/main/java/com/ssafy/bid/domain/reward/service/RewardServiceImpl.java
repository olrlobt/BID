package com.ssafy.bid.domain.reward.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.reward.Reward;
import com.ssafy.bid.domain.reward.dto.RewardListGetResponse;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardSendRequest;
import com.ssafy.bid.domain.reward.repository.RewardRepository;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.repository.UserRepository;
import com.ssafy.bid.global.error.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RewardServiceImpl implements RewardService {

	private final RewardRepository rewardRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;

	@Override
	@Transactional
	public void saveReward(int gradeNo, RewardSaveRequest rewardSaveRequest) {
		Reward reward = rewardSaveRequest.toEntity(gradeNo);
		rewardRepository.save(reward);
	}

	@Override
	public List<RewardListGetResponse> getRewards(int gradeNo) {
		return rewardRepository.findAllByGradeNo(gradeNo);
	}

	@Override
	@Transactional
	public void deleteReward(int rewardNo) {
		boolean exists = rewardRepository.existsByNo(rewardNo);
		if (!exists) {
			throw new ResourceNotFoundException("삭제하려는 Reward 엔티티 없음.", rewardNo);
		}
		rewardRepository.deleteById(rewardNo);
	}

	@Override
	@Transactional
	public void sendReward(RewardSendRequest rewardSendRequest) {
		List<Student> students = userRepository.findAllByIds(rewardSendRequest.getUsersNos());

		if (students.size() != rewardSendRequest.getUsersNos().size()) {
			throw new ResourceNotFoundException("Reward 전송 대상중 확인되지 않은 회원 존재.", rewardSendRequest.getUsersNos());
		}

		students.forEach(
			student -> {
				Reward reward = rewardRepository.findById(rewardSendRequest.getNo())
					.orElseThrow(
						() -> new ResourceNotFoundException("전송하려는 Reward 엔티티 없음.", rewardSendRequest.getNo()));
				student.addRewardPrice(reward.getPrice());
				NotificationRequest notificationRequest = createNotificationRequest(student, reward, rewardSendRequest);
				notificationService.send(notificationRequest);
			}
		);
	}

	private NotificationRequest createNotificationRequest(Student student, Reward reward,
		RewardSendRequest rewardSendRequest) {
		return NotificationRequest.builder()
			.receiverNo(student.getNo())
			.title(reward.getName())
			.content(rewardSendRequest.getComment())
			.notificationType(NotificationType.REWARD)
			.build();
	}
}

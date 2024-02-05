package com.ssafy.bid.domain.reward.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bid.domain.notification.NotificationType;
import com.ssafy.bid.domain.notification.dto.NotificationRequest;
import com.ssafy.bid.domain.notification.service.NotificationService;
import com.ssafy.bid.domain.reward.Reward;
import com.ssafy.bid.domain.reward.dto.RewardSaveRequest;
import com.ssafy.bid.domain.reward.dto.RewardSendRequest;
import com.ssafy.bid.domain.reward.dto.RewardsFindResponse;
import com.ssafy.bid.domain.reward.repository.RewardRepository;
import com.ssafy.bid.domain.user.Student;
import com.ssafy.bid.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class RewardServiceImpl implements RewardService {

	private final RewardRepository rewardRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;

	@Override
	public void saveReward(int gradeNo, RewardSaveRequest rewardSaveRequest) {
		Reward reward = rewardSaveRequest.toEntity(gradeNo);
		rewardRepository.save(reward);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RewardsFindResponse> findRewards(int gradeNo) {
		return rewardRepository.findRewards(gradeNo);
	}

	@Override
	public void deleteReward(int rewardNo) {
		rewardRepository.deleteById(rewardNo);
	}

	@Override
	public void sendReward(RewardSendRequest rewardSendRequest) {
		List<Student> students = userRepository.findAllByIds(rewardSendRequest.getUsersNos());

		if (students.size() != rewardSendRequest.getUsersNos().size()) {
			throw new IllegalArgumentException("전송할 대상 중 확인되지 않은 사용자 존재"); //TODO: 커스텀 예외처리
		}

		students.forEach(
			student -> {
				Reward reward = rewardRepository.findById(rewardSendRequest.getNo())
					.orElseThrow(() -> new IllegalArgumentException("해당하는 리워드 존재 X"));//TODO: 커스텀 예외처리
				student.addRewardPrice(reward.getPrice());
				NotificationRequest notificationRequest = NotificationRequest.builder()
					.receiverNo(student.getNo())
					.title(reward.getName())
					.content(rewardSendRequest.getComment())
					.notificationType(NotificationType.REWARD)
					.build();
				notificationService.send(notificationRequest);
			}
		);
	}
}

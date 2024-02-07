package com.ssafy.bid.infra;

import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;

import com.ssafy.bid.configuration.message.CoolSMSProperties;
import com.ssafy.bid.domain.user.service.MessageService;

@Service
public class CoolSmsMessageService implements MessageService {

	private final DefaultMessageService coolSmsMessageService;
	private final String from;

	public CoolSmsMessageService(CoolSMSProperties coolSMSProperties) {
		this.coolSmsMessageService = NurigoApp.INSTANCE.initialize(
			coolSMSProperties.getApiKey(),
			coolSMSProperties.getApiSecret(),
			coolSMSProperties.getDomain()
		);
		this.from = coolSMSProperties.getFromNumber();
	}

	@Override
	public void sendAuthenticationCode(String to, String code) {
		Message message = new Message();
		message.setFrom(from);
		message.setTo(to);
		message.setText("[B:D] 인증코드 " + code + "를 입력하세요.");

		try {
			coolSmsMessageService.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

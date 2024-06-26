package com.viageria.tripManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
	
	@Autowired
	private EmailService emailService;
	
	public void listen(String message) {
		System.out.println("Received message: " + message);
		
		String to = "testes@gmail.com";
		String subject = "Kafka Notification";
		String text = "New Message Received: " + message;
		emailService.sendMail(to, subject, text);
	}

}

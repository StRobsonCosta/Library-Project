package br.com.gestao.livro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

	@Autowired
	private EmailService emailService;
	
	@KafkaListener(topics = "books", groupId = "library-group")
	public void listen(String message) {
		System.out.println("Received message: " + message);
		
		String to = "kblo.super@gmail.com";
		String subject = "Kafka Notification";
		String text = "New message received: " + message;
		emailService.sendMail(to, subject, text);
	}
}

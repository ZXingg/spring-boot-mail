package com.example.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailTriggerController {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String Sender;

	/*
	*更多邮件形式请看test目录
	* */
	@RequestMapping("/mail")
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(Sender);
		message.setTo(Sender);
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
	}

}

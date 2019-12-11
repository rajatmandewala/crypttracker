package com.rajat.cryp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajat.cryp.cronjob.MailCron;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping(value="/sendmail")
public class MailCheckController {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	private MailCron mailCron;
	
	@GetMapping
	public String sendMail() {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("rajatmandewala@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        //System.out.println(dtf.format(now));
		return "hello=s"+ dtf.format(now);
	}
	
	@GetMapping
	@RequestMapping(value="/sendcryp")
	public String sendCrypMail() {		
		mailCron.getMail();
		return "mail sent successfully.";
	}
	
	

}

package com.rajat.cryp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Calendar;
import java.util.TimeZone;

@RestController
@RequestMapping(value="/sendmail")
public class MailCheckController {
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@GetMapping
	public String sendMail() {
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("rajatmandewala@gmail.com");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
		Calendar c = Calendar.getInstance();
          
        //get current TimeZone using
        TimeZone tz = c.getTimeZone();
          
        System.out.println("Current TimeZone is : " + tz.getDisplayName());
		return "hello"+ tz.getDisplayName();
	}

}

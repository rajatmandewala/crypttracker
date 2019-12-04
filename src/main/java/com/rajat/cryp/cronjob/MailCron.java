package com.rajat.cryp.cronjob;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MailCron {

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Scheduled(cron = " 0 0 10 * * ?")
	public void sendMailFirst() {
		getMail();
	}
	
	@Scheduled(cron = " 0 0 13 * * ?")
	public void sendMailmid() {
		getMail();
	}
	
	@Scheduled(cron = " 0 0 17 * * ?")
	public void sendMailevening() {
		getMail();
	}

	@Scheduled(cron = " 0 00 19 * * ?")
	public void sendMailSecond() {
		System.out.println("rajat");
		getMail();
	}

	public void getMail() {
		final String uri = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,BCH&tsyms=USD";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		try {
			Map<String, Object> response = new ObjectMapper().readValue(result, HashMap.class);
			for (Map.Entry<String, Object> entry : response.entrySet()) {
				entry.setValue(String.valueOf(entry.getValue()).replaceAll(".*\\{USD=|\\}.*", ""));
			}
			
			String message="value of the Bitcoin is"+response.get("BTC")+" AND VALUE OF ETHREUM IS "+response.get("ETH");
			sendMailToUser(message);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
	
	public void sendMailToUser(String message) {
		
		SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("rajatmandewala@gmail.com");

        msg.setSubject("Current Value of the cryptocurrency");
        msg.setText(message);

        javaMailSender.send(msg);
	}
}
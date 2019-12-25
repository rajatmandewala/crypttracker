package com.rajat.cryp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajat.cryp.service.NSEService;

@RestController
@RequestMapping(value="/nse")
public class NSEController {
	
	@Autowired
	private NSEService nseService;
	
	@GetMapping
	public String getFile() throws IOException {
		return nseService.downloadNseFile();
	}
	
	@GetMapping
	@RequestMapping("/s")
	public String get() throws IOException {
		File f=new File("a.txt");
		return f.getCanonicalPath().toString();
	}
	
	

}

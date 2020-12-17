package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.utility.CrawlerChecker;

@Controller
public class SearchingController {
	
	

	@RequestMapping(value="/searching")
	public String Searching()
	{
		return "searching";
	}
	
	@RequestMapping(value ="/searching.do")
	public String nCancel() throws InterruptedException {	//종료 버튼
		CrawlerChecker.stop = true;
		//DriverFactory.getInstance().removeDriver();
		return "index";
	}

	

}

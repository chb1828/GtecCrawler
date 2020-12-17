package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.core.service.NaverCrawler;
import com.example.demo.elasticsearch.service.WebSummaryESService;

@Controller
public class VisualizationController {
	
	@Autowired
	WebSummaryESService webItemEsSvc;

	@RequestMapping(value="/visualization")
	public String Visualization()
	{
		return "visualization";
	}
	
	@RequestMapping(value = "/visualization.s")
	@ResponseBody
	public void allStop() {
		SearchStartController.isStop = true;
	}
	
	@RequestMapping(value = "/visualization.r")
	@ResponseBody
	public void resetDashBoard() {
		webItemEsSvc.resetDashBoard();
	}
}

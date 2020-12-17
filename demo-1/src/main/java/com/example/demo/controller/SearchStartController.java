package com.example.demo.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.core.service.CrawlerThread;
import com.example.demo.core.service.KakaoCrawler;
import com.example.demo.core.service.NaverCrawler;
import com.example.demo.object.CollectObject;
import com.example.demo.utility.CrawlerChecker;

@Controller
public class SearchStartController {

	@Autowired
	NaverCrawler naverCrawler;

	@Autowired
	KakaoCrawler kakaoCrawler;

	ThreadGroup threadGroup = new ThreadGroup("crawlerGroup");
	
	public static boolean isStop = false;

	@RequestMapping(value = "/searchStart")
	public String SearchStart() {
		return "searchStart";
	}

	@RequestMapping(value = "/searchingStart.k")
	@ResponseBody
	public String kSearch(@RequestParam(value = "searchWord") String content,
			@RequestParam(value = "checkBoxes") String[] checkBox) throws UnsupportedEncodingException {
		isStop = false;
		CollectObject collectObject = new CollectObject();

		List<String> listCheckBox = new ArrayList<String>();
		for (int i = 0; i < checkBox.length; i++) {
			listCheckBox.add(checkBox[i]);
		}
		collectObject.setKeyWord(content);
		collectObject.setQualification(listCheckBox);
		collectObject.setPortal("kakao");
		
		CrawlerThread crawlerStart = new CrawlerThread(content, collectObject, kakaoCrawler, threadGroup);
		crawlerStart.start();
		return "visualization";
	}

	@RequestMapping(value = "/searchingStart.n")
	@ResponseBody
	public String nSearch(@RequestParam(value = "searchWord") String content,
			@RequestParam(value = "checkBoxes") String[] checkBox) throws UnsupportedEncodingException {

		isStop = false;
		CollectObject collectObject = new CollectObject();

		List<String> listCheckBox = new ArrayList<String>();

		for (int i = 0; i < checkBox.length; i++) {
			listCheckBox.add(checkBox[i]);
		}
		collectObject.setKeyWord(content);
		collectObject.setQualification(listCheckBox);
		collectObject.setPortal("naver");

		CrawlerThread t1 = new CrawlerThread(content, collectObject, naverCrawler, threadGroup);
		t1.start();
		
		return "visualization";
	}

}


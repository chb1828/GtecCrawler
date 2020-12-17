package com.example.demo.core.service;

import com.example.demo.object.CollectObject;
import com.example.demo.utility.DriverFactory;


public class CrawlerThread extends Thread {
	
	
	CrawlerEngine crawler;
	
	private CollectObject myCollect;
	
	
	
	public CrawlerThread() {
		
	}
	
	public CrawlerThread(String threadName,CollectObject collect, CrawlerEngine ce,ThreadGroup threadGroup) {
		super(threadGroup,threadName);
		myCollect = collect;
		crawler = ce;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			crawler.doCollect(DriverFactory.getInstance().getDriver(), myCollect);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
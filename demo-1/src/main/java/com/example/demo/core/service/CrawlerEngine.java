package com.example.demo.core.service;

import java.io.UnsupportedEncodingException;

import org.openqa.selenium.WebDriver;

import com.example.demo.object.CollectObject;

public interface CrawlerEngine {
	void doCollect(WebDriver driver, CollectObject collectObject) throws UnsupportedEncodingException;
}

package com.example.demo.utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

public class CrawlerChecker {
	
	public static boolean stop = false;

	public static void killPro(WebDriver driver) {
		try {
			driver.close();
			driver.quit();
			Runtime.getRuntime().exec("taskkill / F / IM ChromeDriver.exe / T");
			CrawlerUtil.initSeleniumWebDriver();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package com.example.demo.utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

	private static DriverFactory instance = new DriverFactory();

	public static DriverFactory getInstance() {
		return instance;
	}

	ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() {

		@Override
		protected WebDriver initialValue() {
			System.setProperty("java.net.preferIPv4Stack", "true");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/web-driver/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			//options.addArguments("headless");
			//options.addArguments("window-size=1920x1080");
			//options.addArguments("disable-gpu");
			return new ChromeDriver(options);
		}
	};

	public WebDriver getDriver() {
		return driver.get();
	}

	public void removeDriver() {
		try {
			driver.get().quit();
			driver.set(null);
			driver.remove();
			Runtime.getRuntime().exec("taskkill / F / IM ChromeDriver.exe / T");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
}

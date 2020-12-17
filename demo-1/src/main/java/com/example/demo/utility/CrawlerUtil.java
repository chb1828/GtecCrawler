package com.example.demo.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.RequestMethod;

public class CrawlerUtil {
	
	private static WebDriver driver =null;
	
	public static WebDriver getSeleniumWebDriver() {
		System.setProperty("webdriver.chrome.driver", "/main/resources/web-driver/chromedriver.exe");
		if(driver == null) {
			driver = new ChromeDriver();
		}
		return driver;
	}
	
	public static void initSeleniumWebDriver() {
		driver = null;
	}
	
	public static boolean elementIdExist(WebDriver driver,String frameName) {
		try {
			driver.findElement(By.id(frameName));
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean elementNameExist(WebDriver driver,String frameName) {
		try {
			driver.findElement(By.name(frameName));
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean frameOverlapExist(WebDriver driver,String xpathFrameName) {
		try {
			driver.findElement(By.xpath("//*[contains(@name, 'if_b')]"));
		}catch(Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static String RequestAPI(String apiURL,RequestMethod method,Map<String,String> header) throws Exception {
		HttpURLConnection con;
		URL url = new URL((String) apiURL);	//apiURL : 주소
		
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod(method.toString());

		Set<Entry<String, String>> set = header.entrySet(); //	네이버 키값 가져오기 위해서 사용,	entrySet는 키값과 밸류값 다 가져올때 사용
		Iterator<Entry<String, String>> itr = set.iterator(); // hasNext() 함수 사용 위해서 iterator 사용
		while (itr.hasNext()) {
			Map.Entry<String, String> e = (Map.Entry<String, String>) itr.next();
			con.setRequestProperty(e.getKey(), e.getValue());
		}
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else { // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();

		return response.toString();
		
	}
}

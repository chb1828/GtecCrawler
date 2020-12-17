package com.example.demo.core.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.annotation.PostConstruct;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.OpenAPIConfig;
import com.example.demo.controller.SearchStartController;
import com.example.demo.elasticsearch.service.CollectESService;
import com.example.demo.elasticsearch.service.WebSummaryESService;
import com.example.demo.object.CollectObject;
import com.example.demo.object.ItemObject;
import com.example.demo.object.NaverObject;
import com.example.demo.utility.CrawlerUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class NaverCrawler implements CrawlerEngine {

	@Autowired
	OpenAPIConfig apiConifg;

	@Autowired
	WebSummaryESService webItemEsSvc;

	@Autowired
	CollectESService collectEsSvc;

	public LinkedHashMap<String, Object> config;
	private LinkedHashMap<String, Object> urls;
	private HashMap<String, String> httpHeader;
	private int display;
	private String sort;
	private Gson gson;
	private GsonBuilder gsonB;



	private final String[] searchClasses = { ".se-main-container", "#postViewArea", ".se-module se-module-text" }; // iFrame
																													// 태그

	/*
	 * TODO : api limit 제어(하루에 25000개)
	 */
//	private int limit;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		this.config = (LinkedHashMap<String, Object>) this.apiConifg.getOpenapis().get("naver");
		this.urls = (LinkedHashMap<String, Object>) this.config.get("urls");
		this.httpHeader = new HashMap<String, String>();
		this.httpHeader.put("X-Naver-Client-Id", (String) this.config.get("client-id"));
		this.httpHeader.put("X-Naver-Client-Secret", (String) this.config.get("client-secret"));

		this.display = 10;
		this.sort = "sim"; // sim : 유사도 / date : 최근일
		gsonB = new GsonBuilder();
		gson = gsonB.create();
	}

	public void doCollect(WebDriver driver, CollectObject collectObject) throws UnsupportedEncodingException {
		Iterator<String> apiTypes = this.urls.keySet().iterator();
		String keyword = collectObject.getKeyWord();
		String text = URLEncoder.encode(keyword, "UTF-8");
		while (apiTypes.hasNext()) {
			String apiType = apiTypes.next();
			String apiURL = (String) this.urls.get(apiType);
			apiURL = String.format("%s?query=%s", apiURL, text);
			String restResult = null;
			for (int start = 1; start <= 1000; start += this.display) { // start 검색 시작위치 display 출력건수
				NaverObject restObj = null;

				for (int i = 0; i < collectObject.getQualification().size(); i++) {

					try {
						if (apiType.equals("web") && collectObject.getQualification().get(i).equals("CbWeb")) {
							restResult = collectWeb(String.valueOf(apiURL), start);
						} else if (apiType.equals("blog") && collectObject.getQualification().get(i).equals("CbBlog")) {
							restResult = collectBlogAndNews(String.valueOf(apiURL), start);
						} else if (apiType.equals("news") && collectObject.getQualification().get(i).equals("CbNews")) {
							restResult = collectBlogAndNews(String.valueOf(apiURL), start);
						} else if (collectObject.getQualification().get(i).equals("All")) {
							restResult = collectBlogAndNews(String.valueOf(apiURL), start);
						} else {
							continue;
						}

					} catch (Exception e) {
						continue;
					}

					restObj = gson.fromJson(restResult, NaverObject.class);
					if (restObj != null) {
						try {
							for (int j = 0; j < restObj.getItems().size(); j++) {
								if (!SearchStartController.isStop) {
									// save item!!
									ItemObject item = restObj.getItems().get(j);
									item.setCollectId(collectObject.getId());
									item.setKeyWord(keyword);
									item.setType(apiType);

									/*
									 * Crawling core
									 */

									String htmlText = null;
									try {
										// int startIndex = restResult.indexOf("link")+8;
										// int endIndex = restResult.indexOf("description")-3;
										// String link = restResult.substring(startIndex, endIndex);
										String link = item.getLink();
										if (link.contains("?Redirect=Log&amp;logNo=")) {
											link = link.replace("?Redirect=Log&amp;logNo=", "/");
										}
										driver.get(link);
										Alert alt = driver.switchTo().alert();

										if (alt != null)
											alt.dismiss();
									} catch (Exception e) {

									}
									try {
										if (apiType.equals("blog")) {
											WebElement frame = null;
											do {
												frame = null;
												if (CrawlerUtil.elementIdExist(driver, "mainFrame")) { // 메인프레임 찾기
													frame = driver.findElement(By.id("mainFrame"));
												} else if (CrawlerUtil.elementIdExist(driver, "screenFrame")) {
													frame = driver.findElement(By.id("screenFrame"));
												}
												if (frame != null) {
													driver.switchTo().frame(frame); // 적용
												}
											} while (frame != null);
											for (int k = 0; k < searchClasses.length; k++) {
												try {
													try {
														WebDriverWait wait = new WebDriverWait(driver, 2);
														wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
																By.className(searchClasses[k])));// 요소가 표시 될 때까지 기다립니다
														htmlText = driver.findElement(By.cssSelector(searchClasses[k]))
																.getText();
														// System.out.println(htmlText);
													} catch (Exception e) {
														htmlText = driver.findElement(By.cssSelector(searchClasses[k]))
																.getText();
														// System.out.println(htmlText);
													}
												} catch (Exception e) {
													continue;
												}

												if (htmlText != null) {
													break;
												}
											}
										}
										if (htmlText == null) {
											htmlText = driver.findElement(By.tagName("body")).getText();
										}
										if (htmlText.contains("\\x00")) {
											htmlText = htmlText.replaceAll("[\\x00]", "");
										}
										// System.out.println(htmlText);
									} catch (Exception e) {
										htmlText = "";
									}
									/*
									 * if(htmlText!=null) { //api를 통해 찾은 데이터가 null이 아닌경우 keyword가 포함되어 있어야 한다.
									 * if(!htmlText.contains(keyword)) { htmlText=""; } }
									 */
									item.setHtmlText(htmlText);
									webItemEsSvc.save(item);
								} else {
									System.out.println("네이버 크롤러 종료");
									driver.quit();
									return;
								}

							}
						} catch (Exception e) {
							System.out.println("검색데이터 없음");
							driver.quit();
							return;
						}
					} else {
						System.out.println(apiType + "의 데이터가 없습니다.");
					}
				}
			}
		}
	}

	public String collectWeb(String apiURL, int start) throws Exception {
		apiURL = String.format("%s&display=%d&start=%d", apiURL, this.display, start);
		return CrawlerUtil.RequestAPI(apiURL, RequestMethod.GET, this.httpHeader);
	}

	public String collectBlogAndNews(String apiURL, int start) throws Exception {
		apiURL = String.format("%s&display=%d&start=%d&sort=%s", apiURL, this.display, start, this.sort);
		return CrawlerUtil.RequestAPI(apiURL, RequestMethod.GET, this.httpHeader);
	}
}


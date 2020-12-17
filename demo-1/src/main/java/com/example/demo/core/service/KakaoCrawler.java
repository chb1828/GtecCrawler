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
import com.example.demo.object.KakaoObject;
import com.example.demo.utility.CrawlerUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class KakaoCrawler implements CrawlerEngine {

	@Autowired
	OpenAPIConfig apiConfig;

	@Autowired
	WebSummaryESService webItemEsSvc;

	@Autowired
	CollectESService collectEsSvc;

	private LinkedHashMap<String, Object> config;
	private LinkedHashMap<String, Object> urls;
	private HashMap<String, String> httpHeader;
	private int size; // 한 페이지에 보여질 문서의 개수
	private String sort;
	private Gson gson;
	private GsonBuilder gsonB;

	private final String[] searchClasses = { "#cContent" }; // #contentDiv,.cContentBody

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		this.config = (LinkedHashMap<String, Object>) this.apiConfig.getOpenapis().get("kakao");
		this.urls = (LinkedHashMap<String, Object>) config.get("urls");
		this.httpHeader = new HashMap<String, String>();
		this.httpHeader.put("Authorization", (String) ("KakaoAK " + this.config.get("restapi-key")));
		this.size = 10;
		this.sort = "accuracy"; // accuracy : 정확도순 / recency : 최신순
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
			for (int start = 1; start <= 50; start++) {
				KakaoObject restObj = null;
				for (int i = 0; i < collectObject.getQualification().size(); i++) {
					try {
						if (apiType.equals("web") && collectObject.getQualification().get(i).equals("CbWeb")) {
							restResult = collectWeb(String.valueOf(apiURL), start);
						} else if (apiType.equals("blog") && collectObject.getQualification().get(i).equals("CbBlog")) {
							restResult = collectWeb(String.valueOf(apiURL), start);
						} else if (collectObject.getQualification().get(i).equals("All")) {
							restResult = collectWeb(String.valueOf(apiURL), start);
						} else {
							continue;
						}

					} catch (Exception e) {
						continue;
					}

					restObj = gson.fromJson(restResult, KakaoObject.class);
					if (restObj != null) {
						// save item
						try {

							for (int j = 0; j < restObj.getDocuments().size(); j++) {
								if (!SearchStartController.isStop) {
									ItemObject item = restObj.getDocuments().get(j);
									item.setCollectId(collectObject.getId());
									item.setKeyWord(keyword);
									item.setType(apiType);
									/*
									 * Crawling core
									 */
									String htmlText = null;
									try {
										String url = item.getUrl();

										/*
										 * if(link.contains("?Redirect=Log&amp;logNo=")) { link =
										 * link.replace("?Redirect=Log&amp;logNo=", "/"); }
										 */
										driver.get(url);
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
												if (CrawlerUtil.elementNameExist(driver, "BlogMain")) { // 다음 BlogMain
																										// 프레임
																										// 찾기
													frame = driver.findElement(By.name("BlogMain")); // BlogMain
													driver.switchTo().frame(frame);
													if (CrawlerUtil.frameOverlapExist(driver,
															"//*[contains(@name, 'if_b')]")) {
														frame = driver
																.findElement(By.xpath("//*[contains(@name, 'if_b')]"));
													}
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
																By.className(searchClasses[k])));// 요소가
																									// 표시
																									// 될
																									// 때까지
																									// 기다립니다
														htmlText = driver.findElement(By.cssSelector(searchClasses[k]))
																.getText();

													} catch (Exception e) {
														htmlText = driver.findElement(By.cssSelector(searchClasses[k]))
																.getText();

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
									} catch (Exception e) {
										htmlText = "";
									}
									item.setHtmlText(htmlText);
									webItemEsSvc.save(item);
								} else {
									System.out.println("카카오 크롤러 종료");
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
						break;
					}

				}
			}
		}
	}

	public String collectWeb(String apiURL, int page) throws Exception {
		apiURL = String.format("%s&size=%d&page=%d&sort=%s", apiURL, this.size, page, sort);
		return CrawlerUtil.RequestAPI(apiURL, RequestMethod.GET, this.httpHeader);
	}
}

package com.example.demo.object;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "collects", type = "collect")

public class CollectObject {
	@Id
	private long id;
	private String keyWord;
	private String portal;
	private List<String> qualification;
	private Long endDate;
	private Long count;
	private String crawlerStatus;

	public CollectObject() {
		this.id = System.currentTimeMillis();
		this.crawlerStatus = "crawling";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public final String getKeyWord() {
		return keyWord;
	}

	public final void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public List<String> getQualification() {
		return qualification;
	}

	public void setQualification(List<String> qualification) {
		this.qualification = qualification;
	}

	public String getPortal() {
		return portal;
	}

	public void setPortal(String portal) {
		this.portal = portal;
	}

	public Long getEndDate() {
		return endDate;
	}

	public void setEndDate(Long endDate) {
		this.endDate = endDate;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getCrawlerStatus() {
		return crawlerStatus;
	}

	public void setCrawlerStatus(String crawlerStatus) {
		this.crawlerStatus = crawlerStatus;
	}
}
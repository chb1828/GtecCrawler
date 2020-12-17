package com.example.demo.object;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "items", type = "item")
public class ItemObject {
	@Id
	private Number id;
	private Number collectId;
	@Field(type = FieldType.Text)
	private String type;
	@Field(type = FieldType.Text)
	private String title;
	@Field(type = FieldType.Text)
	private String link;
	@Field(type = FieldType.Text)
	private String url;
	@Field(type = FieldType.Text)
	private String keyWord;
	@Field(type = FieldType.Text, analyzer="korean_analyzer", fielddata=true)
	private String htmlText;
	@Field(type = FieldType.Text, analyzer="korean_analyzer", fielddata=true)
	private String description;

	public Number getId() {
		return id;
	}

	public void setId(Number id) {
		this.id = id;
	}

	public Number getCollectId() {
		return collectId;
	}

	public void setCollectId(Number collectId) {
		this.collectId = collectId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getHtmlText() {
		return htmlText;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}

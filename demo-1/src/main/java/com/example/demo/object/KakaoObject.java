package com.example.demo.object;

import java.util.List;

public class KakaoObject {

	private int total_count;
	private int pageable_count;

	private List<ItemObject> documents;

	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}

	public int getPageable_count() {
		return pageable_count;
	}

	public void setPageable_count(int pageable_count) {
		this.pageable_count = pageable_count;
	}

	public List<ItemObject> getDocuments() {
		return documents;
	}

	public void setDocuments(List<ItemObject> documents) {
		this.documents = documents;
	}

}

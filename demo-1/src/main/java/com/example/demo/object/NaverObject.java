package com.example.demo.object;

import java.util.List;

public class NaverObject {

	private int total;
	private int start;
	private int display;
	private String lastBuildDate;

	private List<ItemObject> items;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public String getLastBuildDate() {
		return lastBuildDate;
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
	}

	public List<ItemObject> getItems() {
		return items;
	}

	public void setItems(List<ItemObject> items) {
		this.items = items;
	}

}

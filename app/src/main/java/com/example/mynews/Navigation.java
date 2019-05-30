package com.example.mynews;

public class Navigation {
	private int type;
	private String url;
	private String title;

	public Navigation(int type, String url, String title) {
		this.type = type;
		this.url = url;
		this.title = title;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Navigation [type=" + type + ", url=" + url + ", title=" + title
				+ "]";
	}

}

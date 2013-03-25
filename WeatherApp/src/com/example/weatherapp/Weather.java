package com.example.weatherapp;

import org.apache.commons.lang3.text.WordUtils;

import com.google.gson.annotations.SerializedName;

public class Weather {
	private int id;
	
	private String main;
	
	private String description;
	
	@SerializedName("icon")
	private String iconName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMain() {
		return main;
	}

	public void setMain(String main) {
		this.main = main;
	}

	public String getDescription() {
		return WordUtils.capitalizeFully(description);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconUrl(String iconName) {
		this.iconName = iconName;
	}
}

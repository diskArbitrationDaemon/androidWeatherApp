package com.example.weatherapp;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OpenWeatherMapApiResponse {
	@SerializedName("list")
	private List<Result> results;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}

	public String getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(String calculationTime) {
		this.calculationTime = calculationTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@SerializedName("message")
	private String message; //not certain of message significance in
							//all circumstances
	
    @SerializedName("cod")
    private int cod; //not entirely sure what this does. HTTP code?
    				 //returns 200 a lot of the time...
    
    @SerializedName("calctime")
    private String calculationTime;
    
    @SerializedName("cnt")
    private int count;


}

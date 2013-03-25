package com.example.weatherapp;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class Result {
	
	
	/***
	 * Result Error - i.e. if there is no GPS.
	 */
	private boolean error;
	
	/***
	 * Result Warning - i.e. if the results are loading
	 */
	
	private boolean warning;
	
	/***
	 * Name of the city
	 */
	private String name; 

	
	/***
	 * Main object that contains temperature and other information
	 */
	private Main main;
	
	/***
	 * List of "weather", which consists mostly of descriptions.
	 */
	@SerializedName("weather")
	private List<Weather> weatherList;

	public Result(){
		error = false; //default results constructed from json are
						//clearly non-empty.
	}
	/***
	 * @return the City name
	 */
	public String getName() {
		return name;
	}
	
	/***
	 * @param name Name of the city from the API
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/***
	 * @return Returns the Main object, which contains the
	 * temperature, humidity and pressure.
	 */
	public Main getMain() {
		return main;
	}
	
	/***
	 * Set the Main object
	 * @param main the Main object from the API
	 */
	public void setMain(Main main) {
		this.main = main;
	}
	
	/**
	 * @return Returns a list of the weather
	 *
	 */
	public List<Weather> getWeatherList() {
		return weatherList;
	}
	
	/**
	 * 
	 * @param weatherList Sets a list of weather from the API
	 */
	public void setWeatherList(List<Weather> weatherList) {
		this.weatherList = weatherList;
	}
	
	public boolean isError(){
		return error;
	}
	
	public void setWarning(String message){
		warning = true;
		name = message;
	}
	
	public boolean isWarning(){
		return warning;
	}
	
	public void setError(String message){
		error = true;
		name = message;
	}
	
}

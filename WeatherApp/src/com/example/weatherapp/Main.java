package com.example.weatherapp;

import com.google.gson.annotations.SerializedName;


public class Main {
	
    public static final double KELVIN_TO_CELCIUS = 273.15;

	private double temp;
	
	private double humidity;
	
	private double pressure;
	
	@SerializedName("temp_min")
	double tempMin;
	
	@SerializedName("temp_max")
	double tempMax;

	public double getTempInCelcius() {
		return temp - KELVIN_TO_CELCIUS;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getTempMin() {
		return tempMin;
	}

	public void setTempMin(double tempMin) {
		this.tempMin = tempMin;
	}

	public double getTempMax() {
		return tempMax;
	}

	public void setTempMax(double tempMax) {
		this.tempMax = tempMax;
	}
	
	/**
	 * Given a temperature of type double, returns a rounded whole number.
	 * Not necessary but useful.
	 * @param tem Temperature of type double
	 * @return temperature as whole number.
	 */
	public static long roundTemp (double temp){
		return Math.round(temp);
	}
}

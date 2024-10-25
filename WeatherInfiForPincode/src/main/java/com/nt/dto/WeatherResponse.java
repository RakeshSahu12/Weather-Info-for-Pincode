package com.nt.dto;

import java.util.List;

public class WeatherResponse {
	private List<Weather> weather;
	private Main main;

	// Getters and Setters

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	// Inner classes to match the structure of the JSON response

	public static class Weather {
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	public static class Main {
		private double temp;

		public double getTemp() {
			return temp;
		}

		public void setTemp(double temp) {
			this.temp = temp;
		}
	}
}

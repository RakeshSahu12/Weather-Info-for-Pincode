package com.nt.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.entity.WeatherInfo;
import com.nt.service.WeatherInfoService;

@RestController
@RequestMapping("/api/weather")
public class WeatherInfoController {

	@Autowired
	private WeatherInfoService weatherInfoService;

	@GetMapping("/{pincode}/{date}")
	public WeatherInfo getWeatherInfo(@PathVariable String pincode,
			@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return weatherInfoService.getWeatherInfo(pincode, date);
	}
}

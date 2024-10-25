package com.nt.service;

import java.time.LocalDate;

import com.nt.entity.WeatherInfo;

public interface WeatherInfoService {
    WeatherInfo getWeatherInfo(String pincode, LocalDate date);
}

package com.nt.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.dto.WeatherResponse;
import com.nt.entity.PincodeInfo;
import com.nt.entity.WeatherInfo;
import com.nt.repo.PincodeInfoRepository;
import com.nt.repo.WeatherInfoRepository;
import com.nt.service.WeatherInfoService;

@Service
public class WeatherInfoServiceImpl implements WeatherInfoService {

	@Autowired
	private PincodeInfoRepository pincodeInfoRepository;
	@Autowired
	private WeatherInfoRepository weatherInfoRepository;
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public WeatherInfo getWeatherInfo(String pincode, LocalDate date) {
		Optional<WeatherInfo> cachedWeather = weatherInfoRepository.findByPincodeAndDate(pincode, date);
		if (cachedWeather.isPresent()) {
			return cachedWeather.get();
		}

		PincodeInfo pincodeInfo = getPincodeLatLong(pincode);
		WeatherInfo weatherInfo = fetchWeatherFromApi(pincode, date, pincodeInfo.getLatitude(),
				pincodeInfo.getLongitude());
		weatherInfoRepository.save(weatherInfo);
		return weatherInfo;
	}

	private PincodeInfo getPincodeLatLong(String pincode) {
	    return pincodeInfoRepository.findByPincode(pincode).orElseGet(() -> {
	        String url = "https://api.openweathermap.org/geo/1.0/zip?zip=" + pincode + ",IN&appid=You_Api_Key";
	        PincodeInfo pincodeInfo = restTemplate.getForObject(url, PincodeInfo.class);
	        if (pincodeInfo != null) {
	            pincodeInfoRepository.save(pincodeInfo);
	        } else {
	            System.out.println("Failed to retrieve PincodeInfo for: " + pincode);
	        }
	        return pincodeInfo;
	    });
	}

	private WeatherInfo fetchWeatherFromApi(String pincode, LocalDate date, double lat, double lon) {
	    String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=You_Api_Key";
	    WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

	    if (response == null || response.getWeather() == null || response.getWeather().isEmpty()) {
	        System.out.println("Invalid API response for pincode " + pincode);
	        return createDefaultWeatherInfo(pincode, date);
	    }

	    System.out.println("API Response for pincode " + pincode + ": " + response);
	    System.out.println("Weather Description: " + response.getWeather().get(0).getDescription());
	    System.out.println("Temperature (K): " + response.getMain().getTemp());

	    WeatherInfo weatherInfo = new WeatherInfo();
	    weatherInfo.setPincode(pincode);
	    weatherInfo.setDate(date);

	    double temperatureInCelsius = response.getMain().getTemp() - 273.15;
	    weatherInfo.setWeatherDescription(response.getWeather().get(0).getDescription());
	    weatherInfo.setTemperature(temperatureInCelsius); // Store temperature in Celsius

	    return weatherInfo;
	}

	private WeatherInfo createDefaultWeatherInfo(String pincode, LocalDate date) {
	    WeatherInfo weatherInfo = new WeatherInfo();
	    weatherInfo.setPincode(pincode);
	    weatherInfo.setDate(date);
	    weatherInfo.setWeatherDescription("N/A");
	    weatherInfo.setTemperature(0.0); // Default temperature
	    return weatherInfo;
	}
}
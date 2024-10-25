package com.nt.repo;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nt.entity.WeatherInfo;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Long> {
	Optional<WeatherInfo> findByPincodeAndDate(String pincode, LocalDate date);
}

package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.entity.PincodeInfo;

public interface PincodeInfoRepository extends JpaRepository<PincodeInfo, Long> {
	Optional<PincodeInfo> findByPincode(String pincode);
}

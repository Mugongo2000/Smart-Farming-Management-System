package com.Farmmanagement.FarmManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Farmmanagement.FarmManagementSystem.entity.FarmProfile;

public interface FarmProfileRepository extends JpaRepository<FarmProfile, Long>{

    List<FarmProfile> findBySoilTypeContainingIgnoreCase(String soilType);
    
}

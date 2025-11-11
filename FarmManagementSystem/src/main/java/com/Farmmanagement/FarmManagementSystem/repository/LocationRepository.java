package com.Farmmanagement.FarmManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.Farmmanagement.FarmManagementSystem.entity.Location;


@Repository
public interface  LocationRepository extends JpaRepository<Location, Long> {
    
    List<Location> findByCode(String code);
    List<Location> findByNameContaining(String name);
    boolean existsByCode(String code);

}

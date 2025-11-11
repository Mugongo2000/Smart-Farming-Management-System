package com.Farmmanagement.FarmManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.http.StreamingHttpOutputMessage.Body;

import com.Farmmanagement.FarmManagementSystem.entity.Crop;
import java.util.List;

public interface CropRepository extends JpaRepository<Crop, Long>{
 
    List<Crop> findByNameContaining(String name);
    
    boolean existsByName(String name);

    List<Crop> findByVariety(String variety);

    

}

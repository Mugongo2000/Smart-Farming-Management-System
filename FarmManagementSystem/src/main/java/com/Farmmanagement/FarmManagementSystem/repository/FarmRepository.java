package com.Farmmanagement.FarmManagementSystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Farmmanagement.FarmManagementSystem.entity.Farm;

public interface FarmRepository extends JpaRepository <Farm, Long> {

    Page<Farm> findByOwnerId(Long ownerId, Pageable pageable);
    
}

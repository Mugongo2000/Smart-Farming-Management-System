package com.Farmmanagement.FarmManagementSystem.controller;

import com.Farmmanagement.FarmManagementSystem.entity.Farm;
import com.Farmmanagement.FarmManagementSystem.repository.FarmRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmRepository farmRepository;

    // Create a new farm ...
    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm) {
        Farm savedFarm = farmRepository.save(farm);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFarm);
    }

    //Get all farms with pagination and sorting
    @GetMapping
    public Page<Farm> getAllFarms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return farmRepository.findAll(pageable);
    }

    // Get a farm by ID
    @GetMapping("/{id}")
    public ResponseEntity<Farm> getFarmById(@PathVariable Long id) {
        Optional<Farm> farm = farmRepository.findById(id);
        return farm.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // update a farm
    @PutMapping("/{id}")
    public ResponseEntity<Farm> updateFarm(@PathVariable Long id, @RequestBody Farm farmDetails) {
        return farmRepository.findById(id).map(existingFarm -> {
            existingFarm.setName(farmDetails.getName());
            existingFarm.setAreaInHectares(farmDetails.getAreaInHectares());
            existingFarm.setOwner(farmDetails.getOwner());
            existingFarm.setProfile(farmDetails.getProfile());
            existingFarm.setCrops(farmDetails.getCrops());
            Farm updatedFarm = farmRepository.save(existingFarm);
            return ResponseEntity.ok(updatedFarm);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delet a farm
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        if (!farmRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        farmRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
}

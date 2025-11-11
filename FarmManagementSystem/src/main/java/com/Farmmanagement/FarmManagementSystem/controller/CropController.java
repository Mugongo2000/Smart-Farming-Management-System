package com.Farmmanagement.FarmManagementSystem.controller;

import com.Farmmanagement.FarmManagementSystem.entity.Crop;
import com.Farmmanagement.FarmManagementSystem.repository.CropRepository;

import lombok.*;

//import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/crops")
@RequiredArgsConstructor
public class CropController {


    private final CropRepository cropRepository;

    // Creating a new crop
    @PostMapping
    public ResponseEntity<Crop> createCrop(@RequestBody Crop crop) {
        if (cropRepository.existsByName(crop.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Crop savedCrop = cropRepository.save(crop);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCrop);
    }

    // Geting all crops with pagination
    @GetMapping("display")
    public Page<Crop> getAllCrops(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return cropRepository.findAll(pageable);
    }

    // Get a crop by ID
    @GetMapping("/{id}")
    public ResponseEntity<Crop> getCropById(@PathVariable Long id) {
        Optional<Crop> crop = cropRepository.findById(id);
        return crop.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Search crops by name
    @GetMapping("/search")
    public List<Crop> searchCrops(@RequestParam String name) {
        return cropRepository.findByNameContaining(name);
    }

    // Get crop by variety
    @GetMapping("/variety/{variety}")
    public List<Crop> getCropsByVariety(@PathVariable String variety) {
        return cropRepository.findByVariety(variety);
    }

    // find by veriety containing
    @GetMapping("/variety/search")
    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrop(@PathVariable Long id, @RequestBody Crop cropDetails) {
        return cropRepository.findById(id).map(existingCrop -> {
            existingCrop.setName(cropDetails.getName());
            existingCrop.setVariety(cropDetails.getVariety());
            Crop updatedCrop = cropRepository.save(existingCrop);
            return ResponseEntity.ok(updatedCrop);
        }).orElse(ResponseEntity.notFound().build());
    }

    // deleting a crop
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable Long id) {
        if (!cropRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cropRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // updating a crop
    @PutMapping("/{id}")
    public ResponseEntity<Crop> updateCrops(@PathVariable Long id, @RequestBody Crop cropDetails) {
        return cropRepository.findById(id)
                .map(existingCrop -> {
                    existingCrop.setName(cropDetails.getName());
                    existingCrop.setVariety(cropDetails.getVariety());
                    Crop updatedCrop = cropRepository.save(existingCrop);
                    return ResponseEntity.ok(updatedCrop);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
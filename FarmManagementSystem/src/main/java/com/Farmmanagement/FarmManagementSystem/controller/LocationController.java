package com.Farmmanagement.FarmManagementSystem.controller;

import com.Farmmanagement.FarmManagementSystem.entity.Location;
import com.Farmmanagement.FarmManagementSystem.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

     private final LocationRepository locationRepository;

    // Cleating locations 
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Location location) {
        
        // existsByCode // i did this to avoid duprication
        if (location.getCode() != null && locationRepository.existsByCode(location.getCode())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Location with code already exists");
        }

        // handle parent relationship (self reference)
        if (location.getParent() != null && location.getParent().getId() != null) {
            Optional<Location> parent = locationRepository.findById(location.getParent().getId());
            parent.ifPresent(location::setParent);
        }

        Location saved = locationRepository.save(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Displaying locations/ paginatedS
    @GetMapping
    public Page<Location> list(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    // get location by ID
    @GetMapping("/{id}")
    public ResponseEntity<Location> get(@PathVariable Long id) {
        return locationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // updating location
    @PutMapping("/updade/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Location updated) {
        return locationRepository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    existing.setType(updated.getType());
                    existing.setCode(updated.getCode());
                                         
                    if (updated.getParent() != null && updated.getParent().getId() != null) {
                        Optional<Location> parent = locationRepository.findById(updated.getParent().getId());
                        parent.ifPresent(existing::setParent);
                    } else {
                        existing.setParent(null);
                    }

                    Location saved = locationRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delating location
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!locationRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        locationRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Search by province code
    @GetMapping("/search/by-code")
    public ResponseEntity<List<Location>> findByCode(@RequestParam String code) {
        List<Location> locations = locationRepository.findByCode(code);
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(locations);
    }

    // Seaching by name
    @GetMapping("/search/by-name")
    public ResponseEntity<List<Location>> findByName(@RequestParam String name) {
        List<Location> locations = locationRepository.findByNameContaining(name);
        if (locations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(locations);
    }
    
}

package com.Farmmanagement.FarmManagementSystem.controller; 
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Farmmanagement.FarmManagementSystem.entity.FarmProfile;
import com.Farmmanagement.FarmManagementSystem.repository.FarmProfileRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cropProfiles")
@RequiredArgsConstructor
public class FarmProfileController {
    
   private final FarmProfileRepository farmProfileRepository;

    // Create  a new farm profile
    @PostMapping("/create")
    public ResponseEntity<FarmProfile> create(@RequestBody FarmProfile farmProfile) {
        FarmProfile saved = farmProfileRepository.save(farmProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // list farm profiles paginated
    @GetMapping
    public Page<FarmProfile> list(Pageable pageable) {
        return farmProfileRepository.findAll(pageable);
    }

    // displaying farm profile by ID 
    @GetMapping("/{id}")
    public ResponseEntity<FarmProfile> get(@PathVariable Long id) {
        return farmProfileRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // updating the existing farm profiles 
    @PutMapping("/{id}")
    public ResponseEntity<FarmProfile> update(@PathVariable Long id, @RequestBody FarmProfile updated) {
        return farmProfileRepository.findById(id)
                .map(existing -> {
                    existing.setSoilType(updated.getSoilType());
                    existing.setIrrigationType(updated.getIrrigationType());
                    existing.setNotes(updated.getNotes());
                    FarmProfile saved = farmProfileRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // deleting  farm profile
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!farmProfileRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        farmProfileRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // search by soil trype
    @GetMapping("/search/by-soil")
    public ResponseEntity<List<FarmProfile>> searchBySoil(@RequestParam String soilType) {
        List<FarmProfile> farms = farmProfileRepository.findBySoilTypeContainingIgnoreCase(soilType);
        if (farms.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(farms);
    }
}

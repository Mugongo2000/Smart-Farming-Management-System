package com.Farmmanagement.FarmManagementSystem.controller;

import com.Farmmanagement.FarmManagementSystem.entity.User;
import com.Farmmanagement.FarmManagementSystem.repository.UserRepository;
//import com.Farmmanagement.FarmManagementSystem.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/users")

@RequiredArgsConstructor
public class UserController {

private final UserRepository userRepository;
//private final LocationRepository locationRepository;


// creating user
@PostMapping
public ResponseEntity<User> create(@RequestBody User user) {
if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
return ResponseEntity.status(HttpStatus.CONFLICT).build();
}
User saved = userRepository.save(user);
return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}


// reading single
@GetMapping("/{id}")
public ResponseEntity<User> get(@PathVariable Long id) {
return userRepository.findById(id)
.map(ResponseEntity::ok)
.orElse(ResponseEntity.notFound().build());
}


// updating a user
@PutMapping("/{id}")
public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User u) {
return userRepository.findById(id)
.map(existing -> {
existing.setFirstName(u.getFirstName());
existing.setLastName(u.getLastName());
existing.setEmail(u.getEmail());
existing.setLocation(u.getLocation());
userRepository.save(existing);
return ResponseEntity.ok(existing);
}).orElse(ResponseEntity.notFound().build());
}


// deleting a user
@DeleteMapping("/{id}")
public ResponseEntity<Void> delete(@PathVariable Long id) {
if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
userRepository.deleteById(id);
return ResponseEntity.noContent().build();
}


// list with pagination & sorting
// example: /api/users?page=0&size=5&sort=firstName,asc
@GetMapping
public Page<User> list(Pageable pageable) {
return userRepository.findAll(pageable);
}
    
}

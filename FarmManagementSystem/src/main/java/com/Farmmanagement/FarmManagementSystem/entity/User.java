package com.Farmmanagement.FarmManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String firstName;
private String lastName;
private String email;


// many users belong to one location
@ManyToOne
@JoinColumn(name = "location_id")
private Location location;


// one user can own many farms
@OneToMany(mappedBy = "owner")
private List<Farm> farms;
    
}

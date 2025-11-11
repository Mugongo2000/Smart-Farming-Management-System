package com.Farmmanagement.FarmManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Farm {
 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String name;
private Double areaInHectares;


// Many farms belong to one owner (User)
@ManyToOne
@JoinColumn(name = "owner_id")
private User owner;


// One-to-one
@OneToOne(cascade = CascadeType.ALL)
@JoinColumn(name = "profile_id", referencedColumnName = "id")
private FarmProfile profile;


// Many-to-many with crops
@ManyToMany
@JoinTable(
name = "farm_crop",
joinColumns = @JoinColumn(name = "farm_id"),
inverseJoinColumns = @JoinColumn(name = "crop_id")
)
private Set<Crop> crops;
}

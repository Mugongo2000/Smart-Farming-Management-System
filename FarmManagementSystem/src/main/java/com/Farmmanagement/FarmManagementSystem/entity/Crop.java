package com.Farmmanagement.FarmManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Crop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String name;
private String variety;


@ManyToMany(mappedBy = "crops")
private Set<Farm> farms;
    
}

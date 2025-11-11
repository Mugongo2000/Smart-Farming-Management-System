package com.Farmmanagement.FarmManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String name; 
private String type;
private String code;


// self reference: parent location
@ManyToOne
@JoinColumn(name = "parent_id")
private Location parent;


@OneToMany(mappedBy = "parent")
private List<Location> children;
    
}

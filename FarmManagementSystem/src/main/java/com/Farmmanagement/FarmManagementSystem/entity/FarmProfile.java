package com.Farmmanagement.FarmManagementSystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FarmProfile {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


private String soilType;
private String irrigationType;
private String notes;

}

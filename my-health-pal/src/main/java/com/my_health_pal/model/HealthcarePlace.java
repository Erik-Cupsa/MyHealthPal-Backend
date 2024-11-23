package com.my_health_pal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "healthcare_places")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthcarePlace {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(nullable = false)
      private String name;

      @Column(nullable = false)
      private String address;

      @Column(nullable = false)
      private double latitude;

      @Column(nullable = false)
      private double longitude;

      @Column(nullable = false)
      private String imageUrl;
}

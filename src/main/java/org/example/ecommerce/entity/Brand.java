package org.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, unique = true, length = 100)
        private String name;

        @Column(columnDefinition = "TEXT")
        private String description;

        @Column(name = "created_at", updatable = false)
        private LocalDateTime createdAt;

        @Column(name = "updated_at")
        private LocalDateTime updatedAt;

//        @PrePersist
//        public void prePersist() {
//                createdAt = LocalDateTime.now();
//                updatedAt = LocalDateTime.now();
//        }
//
//        @PreUpdate
//        public void preUpdate() {
//                updatedAt = LocalDateTime.now();
//        }
}
package com.ms_resenas.resenas.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagen_resena")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenResena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resena_id", nullable = false)
    private Resena resena; // FK real dentro de la misma BD

    @Column(name = "imagen_id", nullable = false)
    private Long imagenId; // [REF LOGICA] -> ms-storage

    @Column(nullable = false)
    private Integer orden = 0;

}

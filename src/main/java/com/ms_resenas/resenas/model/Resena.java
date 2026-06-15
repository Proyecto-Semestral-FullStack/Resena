package com.ms_resenas.resenas.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resena", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "producto_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId; // [REF LOGICA] -> ms-usuarios

    @Column(name = "producto_id", nullable = false)
    private Long productoId; // [REF LOGICA] -> ms-catalogo

    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId; // [REF LOGICA] -> ms-pedido (verifica compra previa)

    @Column(nullable = false)
    private Integer puntuacion; // 1 a 5 estrellas

    @Column(length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(nullable = false)
    private Boolean verificada = false; // TRUE si se confirma compra previa

}

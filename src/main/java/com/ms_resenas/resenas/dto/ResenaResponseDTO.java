package com.ms_resenas.resenas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Schema(description = "Datos de una reseña retornados por el sistema")
public class ResenaResponseDTO {
    @Schema(description = "ID único de la reseña", example = "1")
    private Long id;

    @Schema(description = "ID del usuario que realizó la reseña", example = "5")
    private Long usuarioId;

    @Schema(description = "ID del producto reseñado", example = "1")
    private Long productoId;

    @Schema(description = "Puntuación otorgada al producto (1 a 5 estrellas)", example = "4")
    private Integer puntuacion;

    @Schema(description = "Título de la reseña", example = "Excelente figura, muy detallada")
    private String titulo;

    @Schema(description = "Comentario completo de la reseña", example = "La figura llegó en perfecto estado, los colores son fieles al anime y la base es muy estable")
    private String comentario;

    @Schema(description = "Indica si la compra fue verificada en ms-pedido. Solo las reseñas verificadas son publicadas", example = "true")
    private Boolean verificada;

    @Schema(description = "Lista de IDs de imágenes asociadas a la reseña almacenadas en ms-storage", example = "[3, 7]")
    private List<Long> imagenIds;

    @Schema(description = "Fecha y hora en que se creó la reseña", example = "2025-06-15T14:30:00")
    private LocalDateTime creadoEn;
}

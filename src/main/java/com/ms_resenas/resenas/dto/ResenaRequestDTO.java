package com.ms_resenas.resenas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

@Schema(description = "Datos requeridos para crear una reseña. El usuario debe haber comprado el producto previamente")
public class ResenaRequestDTO {

    @Schema(description = "ID del producto que se está reseñando", example = "1")
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Schema(description = "ID del usuario que realiza la reseña", example = "5")
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @Schema(description = "Puntuación del producto del 1 al 5 estrellas", example = "4", minimum = "1", maximum = "5")
    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 1, message = "La puntuación mínima es 1")
    @Max(value = 5, message = "La puntuación máxima es 5")
    private Integer puntuacion;

    @Schema(description = "Título breve de la reseña (opcional)", example = "Excelente figura, muy detallada", maxLength = 150)
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    private String titulo;

    @Schema(description = "Comentario extendido de la reseña (opcional)", example = "La figura llegó en perfecto estado, los colores son fieles al anime y la base es muy estable")
    private String comentario;
}

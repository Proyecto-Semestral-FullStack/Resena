package com.ms_resenas.resenas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Datos del archivo retornados por ms-storage tras una subida exitosa")
public class ArchivoResponseDTO {

    @Schema(description = "ID único del archivo en ms-storage", example = "7")
    private Long id;

    @Schema(description = "Nombre original del archivo subido", example = "foto_figura_goku.jpg")
    private String nombreOriginal;

    @Schema(description = "URL de descarga del archivo desde Google Cloud Storage", example = "https://storage.googleapis.com/frikitienda_img/foto_figura_goku.jpg")
    private String urlDescarga;
}

package com.ms_resenas.resenas.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ResenaResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long productoId;
    private Integer puntuacion;
    private String titulo;
    private String comentario;
    private Boolean verificada;
    private List<Long> imagenIds;
    private LocalDateTime creadoEn;
}

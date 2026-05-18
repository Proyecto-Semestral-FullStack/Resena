package com.ms_resenas.resenas.controller;

import com.ms_resenas.resenas.dto.ResenaResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ms_resenas.resenas.dto.ResenaRequestDTO;
import com.ms_resenas.resenas.dto.ResenaResponseDTO;
import com.ms_resenas.resenas.service.ResenaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@RequiredArgsConstructor
public class ResenaController {

    private final ResenaService resenaService;

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<ResenaResponseDTO>> listar(@PathVariable Long productoId) {
        List<ResenaResponseDTO> resenas = resenaService.listarPorProducto(productoId);
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResenaResponseDTO> crear(@Valid @RequestBody ResenaRequestDTO dto) {
        ResenaResponseDTO creada = resenaService.crearResena(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @PostMapping(path = "/{id}/imagenes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> agregarImagen(@PathVariable Long id,
                                              @RequestPart("archivo") MultipartFile archivo) {
        resenaService.agregarImagen(id, archivo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

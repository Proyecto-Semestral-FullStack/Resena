package com.ms_resenas.resenas.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_resenas.resenas.controller.ResenaController;
import com.ms_resenas.resenas.dto.ResenaRequestDTO;
import com.ms_resenas.resenas.dto.ResenaResponseDTO;
import com.ms_resenas.resenas.service.ResenaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ResenaController.class)
public class ResenaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ResenaService resenaService;

    // ============================
    // GET /api/resenas/producto/{productoId}
    // ============================

    @Test
    void listar_givenProductoConResenas_returns200() throws Exception {
        // Given
        ResenaResponseDTO dto = ResenaResponseDTO.builder()
                .id(1L)
                .usuarioId(10L)
                .productoId(5L)
                .puntuacion(4)
                .titulo("Muy bueno")
                .verificada(true)
                .build();

        when(resenaService.listarPorProducto(5L)).thenReturn(List.of(dto));

        // When / Then
        mockMvc.perform(get("/api/resenas/producto/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].puntuacion").value(4))
                .andExpect(jsonPath("$[0].titulo").value("Muy bueno"));

        verify(resenaService, times(1)).listarPorProducto(5L);
    }

    @Test
    void listar_givenProductoSinResenas_returns204() throws Exception {
        // Given
        when(resenaService.listarPorProducto(99L)).thenReturn(Collections.emptyList());

        // When / Then
        // Lista vacía → el controller devuelve 204 No Content
        mockMvc.perform(get("/api/resenas/producto/99"))
                .andExpect(status().isNoContent());

        verify(resenaService, times(1)).listarPorProducto(99L);
    }

    // ============================
    // POST /api/resenas
    // ============================

    @Test
    void crear_givenValidRequest_returns201() throws Exception {
        // Given
        ResenaRequestDTO request = new ResenaRequestDTO();
        request.setUsuarioId(10L);
        request.setProductoId(5L);
        request.setPuntuacion(5);
        request.setTitulo("Increíble");
        request.setComentario("Lo recomiendo");

        ResenaResponseDTO response = ResenaResponseDTO.builder()
                .id(1L)
                .usuarioId(10L)
                .productoId(5L)
                .puntuacion(5)
                .titulo("Increíble")
                .verificada(true)
                .build();

        when(resenaService.crearResena(any(ResenaRequestDTO.class))).thenReturn(response);

        // When / Then
        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.puntuacion").value(5))
                .andExpect(jsonPath("$.titulo").value("Increíble"));

        verify(resenaService, times(1)).crearResena(any(ResenaRequestDTO.class));
    }

}

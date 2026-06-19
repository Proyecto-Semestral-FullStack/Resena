package com.ms_resenas.resenas.service;

import com.ms_resenas.resenas.config.CatalogoClient;
import com.ms_resenas.resenas.config.PedidoClient;
import com.ms_resenas.resenas.config.StorageClient;
import com.ms_resenas.resenas.config.UsuarioClient;
import com.ms_resenas.resenas.dto.ResenaRequestDTO;
import com.ms_resenas.resenas.dto.ResenaResponseDTO;
import com.ms_resenas.resenas.exception.RecursoNoEncontradoException;
import com.ms_resenas.resenas.model.Resena;
import com.ms_resenas.resenas.repository.ImagenResenaRepository;
import com.ms_resenas.resenas.repository.ResenaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResenaServiceTest {

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private ImagenResenaRepository imagenResenaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private CatalogoClient catalogoClient;

    @Mock
    private PedidoClient pedidoClient;

    @Mock
    private StorageClient storageClient;

    @InjectMocks
    private ResenaService resenaService;

    // ============================
    // listarPorProducto
    // ============================

    @Test
    void listarPorProducto_givenProductoConResenas_returnsListOfDTOs() {
        // Given
        Resena resena = Resena.builder()
                .id(1L)
                .usuarioId(10L)
                .productoId(5L)
                .puntuacion(4)
                .titulo("Muy bueno")
                .comentario("Excelente producto")
                .verificada(true)
                .build();

        when(resenaRepository.findByProductoId(5L)).thenReturn(List.of(resena));
        when(imagenResenaRepository.findByResenaId(1L)).thenReturn(Collections.emptyList());

        // When
        List<ResenaResponseDTO> resultado = resenaService.listarPorProducto(5L);

        // Then
        assertEquals(1, resultado.size());
        assertEquals(4, resultado.get(0).getPuntuacion());
        assertEquals("Muy bueno", resultado.get(0).getTitulo());
        verify(resenaRepository, times(1)).findByProductoId(5L);
    }

    // ============================
    // crearResena
    // ============================

    @Test
    void crearResena_givenValidRequest_savesAndReturnsDTO() {
        // Given
        ResenaRequestDTO dto = new ResenaRequestDTO();
        dto.setUsuarioId(10L);
        dto.setProductoId(5L);
        dto.setPuntuacion(5);
        dto.setTitulo("Increíble");
        dto.setComentario("Lo recomiendo");

        // Simulamos que usuario y producto existen (void methods)
        doNothing().when(usuarioClient).validarUsuario(10L);
        doNothing().when(catalogoClient).validarProducto(5L);

        // Simulamos que el usuario SÍ compró el producto
        when(pedidoClient.existeCompra(10L, 5L)).thenReturn(true);

        Resena resenaGuardada = Resena.builder()
                .id(1L)
                .usuarioId(10L)
                .productoId(5L)
                .puntuacion(5)
                .titulo("Increíble")
                .comentario("Lo recomiendo")
                .verificada(true)
                .build();

        when(resenaRepository.save(any(Resena.class))).thenReturn(resenaGuardada);
        when(imagenResenaRepository.findByResenaId(1L)).thenReturn(Collections.emptyList());

        // When
        ResenaResponseDTO resultado = resenaService.crearResena(dto);

        // Then
        assertEquals(1L, resultado.getId());
        assertEquals(5, resultado.getPuntuacion());
        assertEquals("Increíble", resultado.getTitulo());
        verify(pedidoClient, times(1)).existeCompra(10L, 5L);
        verify(resenaRepository, times(1)).save(any(Resena.class));
    }

    @Test
    void crearResena_whenUsuarioNoCompro_throwsIllegalStateException() {
        // Given
        ResenaRequestDTO dto = new ResenaRequestDTO();
        dto.setUsuarioId(10L);
        dto.setProductoId(5L);
        dto.setPuntuacion(3);

        doNothing().when(usuarioClient).validarUsuario(10L);
        doNothing().when(catalogoClient).validarProducto(5L);

        // Simulamos que el usuario NO compró el producto
        when(pedidoClient.existeCompra(10L, 5L)).thenReturn(false);

        // When / Then
        assertThrows(IllegalStateException.class,
                () -> resenaService.crearResena(dto));

        // Nunca debe guardarse la reseña
        verify(resenaRepository, never()).save(any());
    }

}

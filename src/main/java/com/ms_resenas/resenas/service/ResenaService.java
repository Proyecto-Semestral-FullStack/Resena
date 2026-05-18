package com.ms_resenas.resenas.service;

import com.ms_resenas.resenas.dto.ArchivoResponseDTO;
import com.ms_resenas.resenas.dto.ResenaRequestDTO;
import com.ms_resenas.resenas.dto.ResenaResponseDTO;
import com.ms_resenas.resenas.exception.RecursoNoEncontradoException;
import com.ms_resenas.resenas.model.ImagenResena;
import com.ms_resenas.resenas.model.Resena;
import com.ms_resenas.resenas.repository.ImagenResenaRepository;
import com.ms_resenas.resenas.repository.ResenaRepository;
import com.ms_resenas.resenas.webclient.CatalogoClient;
import com.ms_resenas.resenas.webclient.PedidoClient;
import com.ms_resenas.resenas.webclient.StorageClient;
import com.ms_resenas.resenas.webclient.UsuarioClient;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final ImagenResenaRepository imagenResenaRepository;
    private final UsuarioClient usuarioClient;
    private final CatalogoClient catalogoClient;
    private final PedidoClient pedidoClient;
    private final StorageClient storageClient;

    @Transactional(readOnly = true)
    public List<ResenaResponseDTO> listarPorProducto(Long productoId) {
        return resenaRepository.findByProductoId(productoId)
                .stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    public ResenaResponseDTO crearResena(ResenaRequestDTO dto) {
        // Validar usuario y producto
        usuarioClient.validarUsuario(dto.getUsuarioId());
        catalogoClient.validarProducto(dto.getProductoId());

        // Verificar que el usuario haya comprado el producto
        if (!pedidoClient.existeCompra(dto.getUsuarioId(), dto.getProductoId())) {
            throw new IllegalStateException("Solo puedes reseñar productos que hayas comprado.");
        }

        Resena resena = Resena.builder()
                .usuarioId(dto.getUsuarioId())
                .productoId(dto.getProductoId())
                .pedidoId(0L) // Se obtendría del pedido real
                .puntuacion(dto.getPuntuacion())
                .titulo(dto.getTitulo())
                .comentario(dto.getComentario())
                .verificada(true)
                .build();

        resena = resenaRepository.save(resena);
        return convertirADto(resena);
    }

    public void agregarImagen(Long resenaId, MultipartFile archivo) {
        Resena resena = resenaRepository.findById(resenaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reseña no encontrada"));
        ArchivoResponseDTO imagen = storageClient.uploadFile(archivo);
        ImagenResena img = ImagenResena.builder()
                .resena(resena)
                .imagenId(imagen.getId())
                .orden(0)
                .build();
        imagenResenaRepository.save(img);
    }

    private ResenaResponseDTO convertirADto(Resena resena) {
        List<Long> imagenIds = imagenResenaRepository.findByResenaId(resena.getId())
                .stream()
                .map(ImagenResena::getImagenId)
                .collect(Collectors.toList());

        return ResenaResponseDTO.builder()
                .id(resena.getId())
                .usuarioId(resena.getUsuarioId())
                .productoId(resena.getProductoId())
                .puntuacion(resena.getPuntuacion())
                .titulo(resena.getTitulo())
                .comentario(resena.getComentario())
                .verificada(resena.getVerificada())
                .imagenIds(imagenIds)
                .creadoEn(resena.getCreadoEn())
                .build();
    }
}

package com.ms_resenas.resenas.repository;

import com.ms_resenas.resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByProductoId(Long productoId);
    Optional<Resena> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);
}

package com.ms_resenas.resenas.repository;

import com.ms_resenas.resenas.model.ImagenResena;
import com.ms_resenas.resenas.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenResenaRepository  extends JpaRepository<ImagenResena, Long> {
    List<ImagenResena> findByResenaId(Long resenaId);
}

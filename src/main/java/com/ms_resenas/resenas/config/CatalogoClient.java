package com.ms_resenas.resenas.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo")
public interface CatalogoClient {
    @GetMapping("/api/productos/{id}")
    void validarProducto(@PathVariable("id") Long productoId);
}

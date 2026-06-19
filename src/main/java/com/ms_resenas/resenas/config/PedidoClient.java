package com.ms_resenas.resenas.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-pedido")
public interface PedidoClient {
    @GetMapping("/api/pedidos/existe-compra")
    Boolean existeCompra(@RequestParam("usuarioId") Long usuarioId,
                         @RequestParam("productoId") Long productoId);
}

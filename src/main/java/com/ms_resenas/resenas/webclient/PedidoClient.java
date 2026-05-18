package com.ms_resenas.resenas.webclient;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
public class PedidoClient {
    private final WebClient webClient;

    public PedidoClient(@LoadBalanced WebClient.Builder webClientBuilder,
                        @Value("${pedido.service.url}") String pedidoUrl) {
        this.webClient = webClientBuilder.baseUrl(pedidoUrl).build();
    }

    public boolean existeCompra(Long usuarioId, Long productoId) {
        try {
            webClient.get()
                    .uri("/api/pedidos/verificar?usuarioId={usuarioId}&productoId={productoId}", usuarioId, productoId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

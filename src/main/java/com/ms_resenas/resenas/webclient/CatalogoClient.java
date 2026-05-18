package com.ms_resenas.resenas.webclient;


import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class CatalogoClient {
    private final WebClient webClient;
    public CatalogoClient(@LoadBalanced WebClient.Builder webClientBuilder,
                          @Value("${catalogo.service.url}") String catalogoUrl) {
        this.webClient = webClientBuilder.baseUrl(catalogoUrl).build();
    }

    public void validarProducto(Long productoId) {
        webClient.get()
                .uri("/api/productos/{id}", productoId)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

}

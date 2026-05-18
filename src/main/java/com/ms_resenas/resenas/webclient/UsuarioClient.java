package com.ms_resenas.resenas.webclient;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Slf4j
@Component
public class UsuarioClient {
    private final WebClient webClient;

    public UsuarioClient(@LoadBalanced WebClient.Builder webClientBuilder,
                         @Value("${usuario.service.url}") String usuarioUrl) {
        this.webClient = webClientBuilder.baseUrl(usuarioUrl).build();
    }

    public void validarUsuario(Long usuarioId) {
        webClient.get()
                .uri("/api/usuarios/{id}", usuarioId)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

}

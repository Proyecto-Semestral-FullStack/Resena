package com.ms_resenas.resenas.config;


import com.ms_resenas.resenas.model.Resena;
import com.ms_resenas.resenas.repository.ResenaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ResenaRepository resenaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Evitar inserción duplicada si ya existen datos
        if (resenaRepository.count() > 0) {
            log.info("La base de datos de reseñas ya tiene datos. Se omite la inicialización.");
            return;
        }

        log.info(">>> Inicializando reseñas de prueba...");

        // Reseña 1: usuario 1 (Javier), producto 1 (Zelda)
        resenaRepository.save(Resena.builder()
                .usuarioId(1L)
                .productoId(1L)
                .puntuacion(5)
                .titulo("¡Obra maestra!")
                .comentario("El mejor Zelda que he jugado. Gráficos increíbles y una historia muy profunda.")
                .verificada(true)                // ← se marca como compra verificada
                .build());

        // Reseña 2: usuario 2 (Sopaipilla69), producto 3 (PS5)
        resenaRepository.save(Resena.builder()
                .usuarioId(2L)
                .productoId(3L)
                .puntuacion(4)
                .titulo("Buena consola, aunque cara")
                .comentario("La PlayStation 5 es muy potente, pero el precio sigue siendo elevado.")
                .verificada(true)
                .build());

        // Reseña 3: usuario 1 (Javier), producto 5 (Goku)
        resenaRepository.save(Resena.builder()
                .usuarioId(1L)
                .productoId(5L)
                .puntuacion(5)
                .titulo("¡Increíble figura!")
                .comentario("Detalles impresionantes, perfecta para coleccionistas de Dragon Ball.")
                .verificada(true)
                .build());

        log.info(">>> Reseñas de prueba insertadas correctamente.");
    }
}

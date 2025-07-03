package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Notificacion;
import com.letrasypapeles.backend.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Operation(
        summary = "Obtener todas las notificaciones",
        description = "Devuelve una lista con todas las notificaciones registradas"
    )
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodas() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodas();
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
        summary = "Obtener notificación por ID",
        description = "Devuelve una notificación específica según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerPorId(
        @Parameter(description = "ID de la notificación a buscar", required = true)
        @PathVariable Long id) {

        return notificacionService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Obtener notificaciones por ID de cliente",
        description = "Devuelve todas las notificaciones asociadas a un cliente específico"
    )
    @ApiResponse(responseCode = "200", description = "Notificaciones del cliente obtenidas exitosamente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Notificacion>> obtenerPorClienteId(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Long clienteId) {

        List<Notificacion> notificaciones = notificacionService.obtenerPorClienteId(clienteId);
        return ResponseEntity.ok(notificaciones);
    }

    @Operation(
        summary = "Crear una nueva notificación",
        description = "Registra una nueva notificación en el sistema con la fecha actual"
    )
    @ApiResponse(responseCode = "200", description = "Notificación creada exitosamente")
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(
        @Parameter(description = "Datos de la notificación a registrar", required = true)
        @RequestBody Notificacion notificacion) {

        notificacion.setFecha(LocalDateTime.now());
        Notificacion nuevaNotificacion = notificacionService.guardar(notificacion);
        return ResponseEntity.ok(nuevaNotificacion);
    }

    @Operation(
        summary = "Eliminar una notificación",
        description = "Elimina una notificación del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificación eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(
        @Parameter(description = "ID de la notificación a eliminar", required = true)
        @PathVariable Long id) {

        return notificacionService.obtenerPorId(id)
                .map(n -> {
                    notificacionService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

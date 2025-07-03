package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Operation(
        summary = "Obtener todas las reservas",
        description = "Devuelve una lista con todas las reservas registradas en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Reserva>> obtenerTodas() {
        List<Reserva> reservas = reservaService.obtenerTodas();
        return ResponseEntity.ok(reservas);
    }

    @Operation(
        summary = "Obtener reserva por ID",
        description = "Devuelve los datos de una reserva específica según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerPorId(
        @Parameter(description = "ID de la reserva a buscar", required = true)
        @PathVariable Long id) {

        return reservaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Obtener reservas por ID de cliente",
        description = "Devuelve todas las reservas asociadas a un cliente específico"
    )
    @ApiResponse(responseCode = "200", description = "Reservas del cliente obtenidas exitosamente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Reserva>> obtenerPorClienteId(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Long clienteId) {

        List<Reserva> reservas = reservaService.obtenerPorClienteId(clienteId);
        return ResponseEntity.ok(reservas);
    }

    @Operation(
        summary = "Crear una nueva reserva",
        description = "Registra una nueva reserva en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Reserva creada exitosamente")
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(
        @Parameter(description = "Datos de la reserva a registrar", required = true)
        @RequestBody Reserva reserva) {

        Reserva nuevaReserva = reservaService.guardar(reserva);
        return ResponseEntity.ok(nuevaReserva);
    }

    @Operation(
        summary = "Actualizar una reserva existente",
        description = "Modifica los datos de una reserva según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(
        @Parameter(description = "ID de la reserva a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados de la reserva", required = true)
        @RequestBody Reserva reserva) {

        return reservaService.obtenerPorId(id)
                .map(r -> {
                    reserva.setId(id);
                    Reserva reservaActualizada = reservaService.guardar(reserva);
                    return ResponseEntity.ok(reservaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar una reserva",
        description = "Elimina una reserva del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reserva eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(
        @Parameter(description = "ID de la reserva a eliminar", required = true)
        @PathVariable Long id) {

        return reservaService.obtenerPorId(id)
                .map(r -> {
                    reservaService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

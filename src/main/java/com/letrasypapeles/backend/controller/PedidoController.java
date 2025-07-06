package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(
        summary = "Obtener todos los pedidos",
        description = "Devuelve una lista con todos los pedidos registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        List<Pedido> pedidos = pedidoService.obtenerTodos();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(
        summary = "Obtener pedido por ID",
        description = "Devuelve los datos de un pedido específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(
        @Parameter(description = "ID del pedido a buscar", required = true)
        @PathVariable Long id) {

        return pedidoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Obtener pedidos por ID de cliente",
        description = "Devuelve todos los pedidos asociados a un cliente específico"
    )
    @ApiResponse(responseCode = "200", description = "Pedidos del cliente obtenidos exitosamente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> obtenerPorClienteId(
        @Parameter(description = "ID del cliente", required = true)
        @PathVariable Long clienteId) {

        List<Pedido> pedidos = pedidoService.obtenerPorClienteId(clienteId);
        return ResponseEntity.ok(pedidos);
    }

    @Operation(
        summary = "Crear un nuevo pedido",
        description = "Registra un nuevo pedido en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente")
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(
        @Parameter(description = "Datos del pedido a registrar", required = true)
        @RequestBody Pedido pedido) {

        Pedido nuevoPedido = pedidoService.guardar(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    @Operation(
        summary = "Actualizar un pedido existente",
        description = "Modifica los datos de un pedido según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizarPedido(
        @Parameter(description = "ID del pedido a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados del pedido", required = true)
        @RequestBody Pedido pedido) {

        return pedidoService.obtenerPorId(id)
                .map(p -> {
                    pedido.setId(id);
                    Pedido pedidoActualizado = pedidoService.guardar(pedido);
                    return ResponseEntity.ok(pedidoActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar un pedido",
        description = "Elimina un pedido del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(
        @Parameter(description = "ID del pedido a eliminar", required = true)
        @PathVariable Long id) {

        return pedidoService.obtenerPorId(id)
                .map(p -> {
                    pedidoService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
        summary = "Verifica si el usuario tiene rol CLIENTE",
        description = "Devuelve un mensaje si el usuario autenticado tiene el rol CLIENTE"
    )
    @ApiResponse(responseCode = "200", description = "Acceso autorizado como CLIENTE")
    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cliente")
    public ResponseEntity<String> cliente() {
        return ResponseEntity.ok("Eres el cliente");
    }

    @Operation(
        summary = "Obtener todos los clientes",
        description = "Devuelve una lista con todos los clientes registrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        List<Cliente> clientes = clienteService.obtenerTodos();
        return ResponseEntity.ok(clientes);
    }

    @Operation(
        summary = "Obtener cliente por ID",
        description = "Devuelve los datos de un cliente específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(
        @Parameter(description = "ID del cliente a buscar", required = true)
        @PathVariable Long id) {

        return clienteService.obtenerPorId(id)
                .map(cliente -> {
                    cliente.setContraseña(null);
                    return ResponseEntity.ok(cliente);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Registrar un nuevo cliente",
        description = "Crea un nuevo cliente en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Cliente registrado exitosamente")
    @PostMapping("/registro")
    public ResponseEntity<Cliente> registrarCliente(
        @Parameter(description = "Datos del cliente a registrar", required = true)
        @RequestBody Cliente cliente) {

        Cliente nuevoCliente = clienteService.registrarCliente(cliente);
        nuevoCliente.setContraseña(null);
        return ResponseEntity.ok(nuevoCliente);
    }

    @Operation(
        summary = "Actualizar un cliente existente",
        description = "Modifica los datos de un cliente según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(
        @Parameter(description = "ID del cliente a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados del cliente", required = true)
        @RequestBody Cliente cliente) {

        return clienteService.obtenerPorId(id)
                .map(c -> {
                    cliente.setIdCliente(id);
                    Cliente clienteActualizado = clienteService.actualizarCliente(cliente);
                    clienteActualizado.setContraseña(null);
                    return ResponseEntity.ok(clienteActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar un cliente",
        description = "Elimina un cliente del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(
        @Parameter(description = "ID del cliente a eliminar", required = true)
        @PathVariable Long id) {

        return clienteService.obtenerPorId(id)
                .map(c -> {
                    clienteService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

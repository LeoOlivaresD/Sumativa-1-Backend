package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Inventario;
import com.letrasypapeles.backend.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Operation(
        summary = "Obtener todos los inventarios",
        description = "Devuelve una lista con todos los registros de inventario"
    )
    @ApiResponse(responseCode = "200", description = "Lista de inventarios obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Inventario>> obtenerTodos() {
        List<Inventario> inventarios = inventarioService.obtenerTodos();
        return ResponseEntity.ok(inventarios);
    }

    @Operation(
        summary = "Obtener inventario por ID",
        description = "Devuelve un registro de inventario específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerPorId(
        @Parameter(description = "ID del inventario a buscar", required = true)
        @PathVariable Long id) {

        return inventarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Obtener inventarios por ID de producto",
        description = "Devuelve todos los registros de inventario asociados a un producto específico"
    )
    @ApiResponse(responseCode = "200", description = "Inventarios del producto obtenidos exitosamente")
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Inventario>> obtenerPorProductoId(
        @Parameter(description = "ID del producto", required = true)
        @PathVariable Long productoId) {

        List<Inventario> inventarios = inventarioService.obtenerPorProductoId(productoId);
        return ResponseEntity.ok(inventarios);
    }

    @Operation(
        summary = "Obtener inventarios por ID de sucursal",
        description = "Devuelve todos los registros de inventario asociados a una sucursal específica"
    )
    @ApiResponse(responseCode = "200", description = "Inventarios de la sucursal obtenidos exitosamente")
    @GetMapping("/sucursal/{sucursalId}")
    public ResponseEntity<List<Inventario>> obtenerPorSucursalId(
        @Parameter(description = "ID de la sucursal", required = true)
        @PathVariable Long sucursalId) {

        List<Inventario> inventarios = inventarioService.obtenerPorSucursalId(sucursalId);
        return ResponseEntity.ok(inventarios);
    }

    @Operation(
        summary = "Crear un nuevo registro de inventario",
        description = "Registra un nuevo inventario en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Inventario creado exitosamente")
    @PostMapping
    public ResponseEntity<Inventario> crearInventario(
        @Parameter(description = "Datos del inventario a registrar", required = true)
        @RequestBody Inventario inventario) {

        Inventario nuevoInventario = inventarioService.guardar(inventario);
        return ResponseEntity.ok(nuevoInventario);
    }

    @Operation(
        summary = "Actualizar un inventario existente",
        description = "Modifica los datos de un inventario según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> actualizarInventario(
        @Parameter(description = "ID del inventario a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados del inventario", required = true)
        @RequestBody Inventario inventario) {

        return inventarioService.obtenerPorId(id)
                .map(i -> {
                    inventario.setId(id);
                    Inventario inventarioActualizado = inventarioService.guardar(inventario);
                    return ResponseEntity.ok(inventarioActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar un inventario",
        description = "Elimina un registro de inventario del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInventario(
        @Parameter(description = "ID del inventario a eliminar", required = true)
        @PathVariable Long id) {

        return inventarioService.obtenerPorId(id)
                .map(i -> {
                    inventarioService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

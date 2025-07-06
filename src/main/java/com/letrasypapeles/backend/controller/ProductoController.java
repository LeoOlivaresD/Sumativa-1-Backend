package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(
        summary = "Obtener todos los productos",
        description = "Devuelve una lista con todos los productos registrados en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        List<Producto> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }

    @Operation(
        summary = "Obtener producto por ID",
        description = "Devuelve los datos de un producto específico según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(
        @Parameter(description = "ID del producto a buscar", required = true)
        @PathVariable Long id) {

        return productoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear un nuevo producto",
        description = "Registra un nuevo producto en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Producto creado exitosamente")
    @PostMapping
    public ResponseEntity<Producto> crearProducto(
        @Parameter(description = "Datos del producto a registrar", required = true)
        @RequestBody Producto producto) {

        Producto nuevoProducto = productoService.guardar(producto);
        return ResponseEntity.ok(nuevoProducto);
    }

    @Operation(
        summary = "Actualizar un producto existente",
        description = "Modifica los datos de un producto según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
        @Parameter(description = "ID del producto a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados del producto", required = true)
        @RequestBody Producto producto) {

        return productoService.obtenerPorId(id)
                .map(p -> {
                    producto.setId(id);
                    Producto productoActualizado = productoService.guardar(producto);
                    return ResponseEntity.ok(productoActualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar un producto",
        description = "Elimina un producto del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(
        @Parameter(description = "ID del producto a eliminar", required = true)
        @PathVariable Long id) {

        return productoService.obtenerPorId(id)
                .map(p -> {
                    productoService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

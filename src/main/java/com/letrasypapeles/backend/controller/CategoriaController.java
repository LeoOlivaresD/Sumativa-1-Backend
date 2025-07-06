package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
        summary = "Obtener todas las categorías",
        description = "Devuelve una lista con todas las categorías disponibles"
    )
    @ApiResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        List<Categoria> categorias = categoriaService.obtenerTodas();
        return ResponseEntity.ok(categorias);
    }

    @Operation(
        summary = "Obtener categoría por ID",
        description = "Devuelve los datos de una categoría específica según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerPorId(
        @Parameter(description = "ID de la categoría a buscar", required = true)
        @PathVariable Long id) {

        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear una nueva categoría",
        description = "Registra una nueva categoría en el sistema"
    )
    @ApiResponse(responseCode = "200", description = "Categoría creada exitosamente")
    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(
        @Parameter(description = "Datos de la categoría a crear", required = true)
        @RequestBody Categoria categoria) {

        Categoria nuevaCategoria = categoriaService.guardar(categoria);
        return ResponseEntity.ok(nuevaCategoria);
    }

    @Operation(
        summary = "Actualizar una categoría existente",
        description = "Modifica los datos de una categoría según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
        @Parameter(description = "ID de la categoría a actualizar", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados de la categoría", required = true)
        @RequestBody Categoria categoria) {

        return categoriaService.obtenerPorId(id)
                .map(c -> {
                    categoria.setId(id);
                    Categoria categoriaActualizada = categoriaService.guardar(categoria);
                    return ResponseEntity.ok(categoriaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar una categoría",
        description = "Elimina una categoría del sistema según su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(
        @Parameter(description = "ID de la categoría a eliminar", required = true)
        @PathVariable Long id) {

        return categoriaService.obtenerPorId(id)
                .map(c -> {
                    categoriaService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

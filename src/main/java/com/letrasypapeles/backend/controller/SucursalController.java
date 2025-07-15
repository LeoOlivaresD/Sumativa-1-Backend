package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Sucursal;
import com.letrasypapeles.backend.hateoas.SucursalModelAssembler;
import com.letrasypapeles.backend.service.SucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private SucursalModelAssembler sucursalModelAssembler;

    @Operation(summary = "Obtener todas las sucursales",
               description = "Devuelve una lista con todas las sucursales registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de sucursales obtenida exitosamente")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Sucursal>>> obtenerTodas() {
        List<EntityModel<Sucursal>> sucursales = sucursalService.obtenerTodas().stream()
                .map(sucursalModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(sucursales,
                linkTo(methodOn(SucursalController.class).obtenerTodas()).withSelfRel()));
    }

    @Operation(summary = "Obtener sucursal por ID",
               description = "Devuelve los datos de una sucursal específica según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursal encontrada"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> obtenerPorId(
            @Parameter(description = "ID de la sucursal a buscar", required = true)
            @PathVariable Long id) {

        return sucursalService.obtenerPorId(id)
                .map(sucursalModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva sucursal",
               description = "Registra una nueva sucursal en el sistema")
    @ApiResponse(responseCode = "200", description = "Sucursal creada exitosamente")
    @PostMapping
    public ResponseEntity<EntityModel<Sucursal>> crearSucursal(
            @Parameter(description = "Datos de la sucursal a registrar", required = true)
            @RequestBody Sucursal sucursal) {

        Sucursal nuevaSucursal = sucursalService.guardar(sucursal);
        return ResponseEntity.ok(sucursalModelAssembler.toModel(nuevaSucursal));
    }

    @Operation(summary = "Actualizar una sucursal existente",
               description = "Modifica los datos de una sucursal según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursal actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Sucursal>> actualizarSucursal(
            @Parameter(description = "ID de la sucursal a actualizar", required = true)
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la sucursal", required = true)
            @RequestBody Sucursal sucursal) {

        return sucursalService.obtenerPorId(id)
                .map(s -> {
                    sucursal.setId(id);
                    Sucursal actualizada = sucursalService.guardar(sucursal);
                    return ResponseEntity.ok(sucursalModelAssembler.toModel(actualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una sucursal",
               description = "Elimina una sucursal del sistema según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Sucursal eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Sucursal no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSucursal(
            @Parameter(description = "ID de la sucursal a eliminar", required = true)
            @PathVariable Long id) {

        return sucursalService.obtenerPorId(id)
                .map(s -> {
                    sucursalService.eliminar(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

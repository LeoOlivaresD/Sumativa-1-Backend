package com.letrasypapeles.backend.hateoas;

import com.letrasypapeles.backend.controller.SucursalController;
import com.letrasypapeles.backend.entity.Sucursal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SucursalModelAssembler implements RepresentationModelAssembler<Sucursal, EntityModel<Sucursal>> {

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
            linkTo(methodOn(SucursalController.class).obtenerPorId(sucursal.getId())).withSelfRel(),
            linkTo(methodOn(SucursalController.class).obtenerTodas()).withRel("all"),
            linkTo(methodOn(SucursalController.class).actualizarSucursal(sucursal.getId(), sucursal)).withRel("update"),
            linkTo(methodOn(SucursalController.class).eliminarSucursal(sucursal.getId())).withRel("delete")
        );
    }
}

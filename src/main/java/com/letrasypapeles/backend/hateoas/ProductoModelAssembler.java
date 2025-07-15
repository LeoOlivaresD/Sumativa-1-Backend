package com.letrasypapeles.backend.hateoas;

import com.letrasypapeles.backend.controller.ProductoController;
import com.letrasypapeles.backend.entity.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).obtenerPorId(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).obtenerTodos()).withRel("all"),
                linkTo(methodOn(ProductoController.class).actualizarProducto(producto.getId(), null)).withRel("update"),
                linkTo(methodOn(ProductoController.class).eliminarProducto(producto.getId())).withRel("delete")
        );
    }
}


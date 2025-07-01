package com.letrasypapeles.backend.service;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.RoleEntity;
import com.letrasypapeles.backend.repository.ClienteRepository;
import com.letrasypapeles.backend.repository.RoleRepository;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodos() {
        List<Cliente> clientes = List.of(new Cliente(), new Cliente());
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.obtenerTodos();
        assertEquals(2, result.size());
    }

    @Test
    void testObtenerPorId() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.obtenerPorId(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdCliente());
    }

    @Test
    void testObtenerPorEmail() {
        Cliente cliente = new Cliente();
        cliente.setEmail("correo@mail.com");
        when(clienteRepository.findByEmail("correo@mail.com")).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.obtenerPorEmail("correo@mail.com");
        assertTrue(result.isPresent());
        assertEquals("correo@mail.com", result.get().getEmail());
    }

    @Test
    void testRegistrarCliente_ExitosoConRolExistente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("nuevo@mail.com");
        cliente.setContraseña("clave");

        RoleEntity rol = new RoleEntity("CLIENTE");
        when(clienteRepository.existsByEmail("nuevo@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("clave")).thenReturn("clave-codificada");
        when(roleRepository.findByName("CLIENTE")).thenReturn(Optional.of(rol));
        when(clienteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = clienteService.registrarCliente(cliente);

        assertEquals("clave-codificada", resultado.getContraseña());
        assertEquals(0, resultado.getPuntosFidelidad());
        assertTrue(resultado.getRoles().contains(rol));
    }

    @Test
    void testRegistrarCliente_ConRolInexistente() {
        Cliente cliente = new Cliente();
        cliente.setEmail("nuevo@mail.com");
        cliente.setContraseña("clave");

        RoleEntity nuevoRol = new RoleEntity("CLIENTE");
        when(clienteRepository.existsByEmail("nuevo@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("clave")).thenReturn("clave-codificada");
        when(roleRepository.findByName("CLIENTE")).thenReturn(Optional.empty());
        when(roleRepository.save(any())).thenReturn(nuevoRol);
        when(clienteRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente resultado = clienteService.registrarCliente(cliente);

        assertTrue(resultado.getRoles().contains(nuevoRol));
    }

    @Test
    void testRegistrarCliente_YaExiste() {
        Cliente cliente = new Cliente();
        cliente.setEmail("repetido@mail.com");

        when(clienteRepository.existsByEmail("repetido@mail.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> clienteService.registrarCliente(cliente));
        assertEquals("El correo electrónico ya está registrado.", ex.getMessage());
    }

    @Test
    void testActualizarCliente() {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1L);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente actualizado = clienteService.actualizarCliente(cliente);
        assertEquals(1L, actualizado.getIdCliente());
    }

    @Test
    void testEliminar() {
        doNothing().when(clienteRepository).deleteById(1L);
        clienteService.eliminar(1L);
        verify(clienteRepository).deleteById(1L);
    }
}

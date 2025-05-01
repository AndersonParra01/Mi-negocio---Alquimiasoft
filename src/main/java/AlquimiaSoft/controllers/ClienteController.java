package AlquimiaSoft.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AlquimiaSoft.dtos.ClienteDto;
import AlquimiaSoft.dtos.DireccionDto;
import AlquimiaSoft.exception.ExcepcionNegocio;
import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.services.ClienteService;
import AlquimiaSoft.services.DireccionService;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private DireccionService direccionService;

    /**
     * Buscar clientes por nombre o número de identificación
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDto>> buscar(@RequestParam(required = false) String query) {
        if (query == null || query.isBlank()) {
            throw new ExcepcionNegocio("Debe enviar el parámetro 'query' para realizar la búsqueda");
        }
        return ResponseEntity.ok(clienteService.buscarClientes(query));
    }

    /**
     * Listar todos los clientes registrados
     */
    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDto>> listar() {
        List<ClienteDto> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    /**
     * Crear un nuevo cliente con dirección matriz
     */
    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody ClienteDto dto) {
        Cliente cliente = clienteService.crearCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    /**
     * Actualizar datos de un cliente existente
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<ClienteDto> editar(@PathVariable Long id, @Valid @RequestBody ClienteDto dto) {
        ClienteDto actualizado = clienteService.editarCliente(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    /**
     * Eliminar cliente por ID
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Agregar una dirección adicional a un cliente
     */
    @PostMapping("/agregar-direcciones/{id}")
    public ResponseEntity<DireccionDto> agregarDireccion(@PathVariable Long id, @Valid @RequestBody DireccionDto dto) {
        DireccionDto direccion = direccionService.agregarDireccion(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(direccion);
    }

    /**
     * Listar todas las direcciones (incluyendo matriz) de un cliente
     */
    @GetMapping("/direcciones/{id}")
    public ResponseEntity<List<DireccionDto>> listarDirecciones(@PathVariable Long id) {
        List<DireccionDto> direcciones = direccionService.obtenerDirecciones(id);
        return ResponseEntity.ok(direcciones);
    }
}

package AlquimiaSoft.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AlquimiaSoft.dtos.ClienteDto;
import AlquimiaSoft.dtos.DireccionDto;
import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.services.ClienteService;
import AlquimiaSoft.services.DireccionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDto>> buscar(
            @RequestParam String query) {
        List<ClienteDto> resultado = clienteService.buscarClientes(query);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ClienteDto>> listar() {
        List<ClienteDto> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cliente> crearCliente(@RequestBody ClienteDto dto) {
        Cliente cliente = clienteService.crearCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @PutMapping("/actualizar/{id}")
    public ClienteDto editar(@PathVariable Long id, @RequestBody ClienteDto dto) {
        return clienteService.editarCliente(id, dto);
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/agregar-direccion/{id}/")
    @ResponseStatus(HttpStatus.CREATED)
    public DireccionDto agregarDireccion(
            @PathVariable Long id, @Validated @RequestBody DireccionDto dto) {
        return direccionService.agregarDireccion(id, dto);
    }

    @GetMapping("/direcciones/{id}")
    public List<DireccionDto> listarDirecciones(@PathVariable Long id) {
        return direccionService.obtenerDirecciones(id);
    }
}

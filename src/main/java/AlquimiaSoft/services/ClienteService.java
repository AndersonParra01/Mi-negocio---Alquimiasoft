package AlquimiaSoft.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AlquimiaSoft.dtos.ClienteDto;
import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.models.Direccion;
import AlquimiaSoft.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    // Métodos restantes en ClienteService
    public List<ClienteDto> buscarClientes(String query) {
        return clienteRepository
                .findByNumeroIdentificacionContainingIgnoreCaseOrNombresContainingIgnoreCase(query, query)
                .stream().map(ClienteDto::new).toList();
    }

    public Cliente crearCliente(ClienteDto clienteDto) {
        if (clienteRepository.existsByNumeroIdentificacion(clienteDto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("El cliente ya existe con el número de identificación proporcionado.");
        }
        Cliente cliente = new Cliente();
        Direccion direccion = new Direccion();
        direccion.setMatriz(true);
        direccion.setCliente(cliente);
        // agregar dirección
        cliente.getDirecciones().add(direccion);
        return clienteRepository.save(cliente);
    }

    public ClienteDto editarCliente(Long id, ClienteDto dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        if (!cliente.getNumeroIdentificacion().equals(dto.getNumeroIdentificacion()) &&
                clienteRepository.existsByNumeroIdentificacion(dto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("Número de identificación ya existe para otro cliente");
        }
        cliente.setTipoIdentificacion(dto.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(dto.getNumeroIdentificacion());
        cliente.setNombres(dto.getNombres());
        cliente.setCorreo(dto.getCorreo());
        cliente.setCelular(dto.getCelular());
        return new ClienteDto(clienteRepository.save(cliente));
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }

}

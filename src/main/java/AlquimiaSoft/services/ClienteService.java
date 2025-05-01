package AlquimiaSoft.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AlquimiaSoft.dtos.ClienteDto;
import AlquimiaSoft.dtos.DireccionDto;
import AlquimiaSoft.exception.ExcepcionNegocio;
import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.models.Direccion;
import AlquimiaSoft.repositories.ClienteRepository;
import AlquimiaSoft.repositories.DireccionRepository;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DireccionRepository direccionRepository;

    public List<ClienteDto> listarClientes() {
        return clienteRepository.findAll()
                .stream().map(ClienteDto::new).toList();
    }

    // Métodos restantes en ClienteService
    public List<ClienteDto> buscarClientes(String query) {
        return clienteRepository
                .findByNumeroIdentificacionContainingIgnoreCaseOrNombresContainingIgnoreCase(query, query)
                .stream().map(ClienteDto::new).toList();
    }

    public Cliente crearCliente(ClienteDto clienteDto) {

        // Validación de duplicados
        if (clienteRepository.existsByNumeroIdentificacion(clienteDto.getNumeroIdentificacion())) {
            throw new IllegalArgumentException("El cliente ya existe con el número de identificación proporcionado.");
        }

        // Crear entidad Cliente desde el DTO
        Cliente cliente = new Cliente();
        cliente.setTipoIdentificacion(clienteDto.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(clienteDto.getNumeroIdentificacion());
        cliente.setNombres(clienteDto.getNombres());
        cliente.setCorreo(clienteDto.getCorreo());
        cliente.setCelular(clienteDto.getCelular());

        // Validar que el DTO tenga dirección matriz
        DireccionDto direccionDto = clienteDto.getDireccionMatriz();
        if (direccionDto == null) {
            throw new IllegalArgumentException("Debe proporcionar una dirección matriz.");
        }

        // Crear entidad Dirección
        Direccion direccion = new Direccion();
        direccion.setProvincia(direccionDto.getProvincia());
        direccion.setCiudad(direccionDto.getCiudad());
        direccion.setDireccion(direccionDto.getDireccion());
        direccion.setMatriz(true); // matriz obligatoria
        direccion.setCliente(cliente);

        // Asignar dirección al cliente
        cliente.getDirecciones().add(direccion);

        // Guardar y retornar
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
        boolean existe = clienteRepository.existsById(id);
        if (!existe) {
            throw new ExcepcionNegocio("El cliente con ID " + id + " no existe");
        }
        clienteRepository.deleteById(id);
    }

    public List<DireccionDto> obtenerDireccionesPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new NoSuchElementException("Cliente no encontrado con ID: " + clienteId);
        }

        List<Direccion> direcciones = direccionRepository.findByClienteId(clienteId);
        return direcciones.stream()
                .map(DireccionDto::new)
                .collect(Collectors.toList());
    }

}

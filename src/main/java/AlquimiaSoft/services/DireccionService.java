package AlquimiaSoft.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AlquimiaSoft.dtos.DireccionDto;
import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.models.Direccion;
import AlquimiaSoft.repositories.ClienteRepository;
import AlquimiaSoft.repositories.DireccionRepository;

@Service
public class DireccionService {
    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public DireccionDto agregarDireccion(Long clienteId, DireccionDto dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        Direccion direccion = new Direccion();
        direccion.setProvincia(dto.getProvincia());
        direccion.setCiudad(dto.getCiudad());
        direccion.setDireccion(dto.getDireccion());
        direccion.setMatriz(false); // no se permite más de una matriz
        direccion.setCliente(cliente);
        direccionRepository.save(direccion);
        return new DireccionDto(direccion);
    }

    public List<DireccionDto> obtenerDirecciones(Long clienteId) {
        return direccionRepository.findByClienteId(clienteId)
                .stream().map(DireccionDto::new).toList();
    }

}

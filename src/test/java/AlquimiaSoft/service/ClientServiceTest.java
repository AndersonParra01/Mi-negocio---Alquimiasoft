package AlquimiaSoft.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import AlquimiaSoft.dtos.ClienteDto;
import AlquimiaSoft.dtos.DireccionDto;
import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.models.Direccion;
import AlquimiaSoft.repositories.ClienteRepository;
import AlquimiaSoft.repositories.DireccionRepository;
import AlquimiaSoft.services.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private DireccionRepository direccionRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testCrearClienteConDireccionMatriz() {
        Cliente cliente = new Cliente();
        cliente.setNumeroIdentificacion("1234567890");

        Direccion direccion = new Direccion();
        direccion.setMatriz(true);

        when(clienteRepository.existsByNumeroIdentificacion("1234567890")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNumeroIdentificacion("1234567890");
        DireccionDto direccionDto = new DireccionDto();
        direccionDto.setEsMatriz(direccion.isMatriz());
        clienteDto.setDireccionMatriz(direccionDto);

        Cliente result = clienteService.crearCliente(clienteDto);

        assertEquals("1234567890", result.getNumeroIdentificacion());
        verify(clienteRepository).save(any(Cliente.class));
        verify(direccionRepository).save(any(Direccion.class));
    }
}

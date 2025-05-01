package AlquimiaSoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import AlquimiaSoft.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNumeroIdentificacionContainingIgnoreCaseOrNombresContainingIgnoreCase(String numero,
            String nombres);

    boolean existsByNumeroIdentificacion(String numeroIdentificacion);
}

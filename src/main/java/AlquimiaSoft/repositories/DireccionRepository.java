package AlquimiaSoft.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import AlquimiaSoft.models.Direccion;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByClienteId(Long clienteId);
}

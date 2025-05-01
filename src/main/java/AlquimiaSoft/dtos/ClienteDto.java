package AlquimiaSoft.dtos;

import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.models.Direccion;
// DTOs
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    private Long id;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String nombres;
    private String correo;
    private String celular;
    private DireccionDto direccionMatriz; // opcional

    public ClienteDto(Cliente cliente) {
        this.id = cliente.getId();
        this.tipoIdentificacion = cliente.getTipoIdentificacion();
        this.numeroIdentificacion = cliente.getNumeroIdentificacion();
        this.nombres = cliente.getNombres();
        this.correo = cliente.getCorreo();
        this.celular = cliente.getCelular();
        this.direccionMatriz = cliente.getDirecciones().stream()
                .filter(Direccion::isMatriz)
                .findFirst()
                .map(DireccionDto::new)
                .orElse(null);
    }
}

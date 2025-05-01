package AlquimiaSoft.dtos;

import AlquimiaSoft.models.Cliente;
import AlquimiaSoft.models.Direccion;
// DTOs
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {
    private Long id;

    @NotBlank(message = "El tipo de identificación es obligatorio")
    private String tipoIdentificacion;

    @NotBlank(message = "El número de identificación es obligatorio")
    private String numeroIdentificacion;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombres;

    @Email(message = "El correo debe tener un formato válido")
    private String correo;

    @Size(max = 20, message = "El celular no puede exceder los 20 caracteres")
    private String celular;

    @Valid
    @NotNull(message = "Debe incluir una dirección matriz")
    private DireccionDto direccionMatriz;

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

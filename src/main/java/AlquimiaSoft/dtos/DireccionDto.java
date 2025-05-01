package AlquimiaSoft.dtos;

import javax.validation.constraints.NotBlank;

import AlquimiaSoft.models.Direccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDto {
    private Long id;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    @NotBlank(message = "La dirección o calle es obligatoria")
    private String direccion;
    private boolean esMatriz;

    public DireccionDto(Direccion direccion) {
        this.id = direccion.getId();
        this.provincia = direccion.getProvincia();
        this.ciudad = direccion.getCiudad();
        this.direccion = direccion.getDireccion();
        this.esMatriz = direccion.isMatriz();
    }
}
package AlquimiaSoft.dtos;

import AlquimiaSoft.models.Direccion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionDto {
    private Long id;
    private String provincia;
    private String ciudad;
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
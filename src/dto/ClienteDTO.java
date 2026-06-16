package dto;

// DTO para registrar cliente (6 parámetros → supera el límite de 5, se usa DTO)
public class ClienteDTO {

    private String dniCuit;
    private String nombreRazonSocial;
    private String telefono;
    private String email;
    private String direccion;

    public ClienteDTO(String dniCuit, String nombreRazonSocial, String telefono,
                      String email, String direccion) {
        this.dniCuit = dniCuit;
        this.nombreRazonSocial = nombreRazonSocial;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
    }

    public String getDniCuit() {
    	return dniCuit; 
    	}
    public String getNombreRazonSocial() {
    	return nombreRazonSocial; 
    	}
    public String getTelefono() {
    	return telefono; 
    	}
    public String getEmail() {
    	return email;
    	}
    public String getDireccion() {
    	return direccion; 
    	}
}

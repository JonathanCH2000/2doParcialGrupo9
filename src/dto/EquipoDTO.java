package dto;

import mvc.model.TipoEquipo;

// DTO para registrar equipo (7 parámetros → supera el límite de 5, se usa DTO)
public class EquipoDTO {

    private String codigo;
    private String nombre;
    private String descripcion;
    private TipoEquipo tipoEquipo;
    private double valorDiario;
    private int stockInicial;
    private boolean requiereInstalacion;

    public EquipoDTO(String codigo, String nombre, String descripcion, TipoEquipo tipoEquipo,
                     double valorDiario, int stockInicial, boolean requiereInstalacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoEquipo = tipoEquipo;
        this.valorDiario = valorDiario;
        this.stockInicial = stockInicial;
        this.requiereInstalacion = requiereInstalacion;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public TipoEquipo getTipoEquipo() { return tipoEquipo; }
    public double getValorDiario() { return valorDiario; }
    public int getStockInicial() { return stockInicial; }
    public boolean isRequiereInstalacion() { return requiereInstalacion; }
}

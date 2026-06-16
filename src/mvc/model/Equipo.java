package mvc.model;

public class Equipo {

    private String codigo;
    private String nombre;
    private String descripcion;
    private TipoEquipo tipoEquipo;
    private EstadoEquipo estado;
    private double valorDiario;
    private int stockDisponible;
    private boolean requiereInstalacion;

    public Equipo(String codigo, String nombre, String descripcion, TipoEquipo tipoEquipo,
                  double valorDiario, int stockDisponible, boolean requiereInstalacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoEquipo = tipoEquipo;
        this.valorDiario = valorDiario;
        this.stockDisponible = stockDisponible;
        this.requiereInstalacion = requiereInstalacion;
        this.estado = EstadoEquipo.DISPONIBLE;
    }

    public boolean estaDisponible(int cantidad) { return stockDisponible >= cantidad; }
    public void reservarStock(int cantidad) { this.stockDisponible -= cantidad; }
    public void liberarStock(int cantidad) { this.stockDisponible += cantidad; }
    public void cambiarEstado(EstadoEquipo estadoNuevo) { this.estado = estadoNuevo; }
    public boolean coincideCodigo(String codigoEquipo) { return this.codigo.equals(codigoEquipo); }
    public boolean coincideTipoEvento(TipoEquipo tipoEvento) { return this.tipoEquipo == tipoEvento; }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public TipoEquipo getTipoEquipo() { return tipoEquipo; }
    public EstadoEquipo getEstado() { return estado; }
    public double getValorDiario() { return valorDiario; }
    public int getStockDisponible() { return stockDisponible; }
    public boolean isRequiereInstalacion() { return requiereInstalacion; }
}

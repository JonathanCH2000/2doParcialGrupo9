package exceptions;

public class EquipoYaExisteException extends RuntimeException{
    public EquipoYaExisteException(String mensaje){
        super(mensaje);
    }
}
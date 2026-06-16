package exceptions;

public class AlquilerNoValidoException extends RuntimeException{
    public AlquilerNoValidoException(String mensaje){
        super(mensaje);
    }
}
package exceptions;

public class AlquilerNoEncontradoException extends RuntimeException{
    public AlquilerNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
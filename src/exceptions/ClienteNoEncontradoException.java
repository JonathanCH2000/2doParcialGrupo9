package exceptions;

public class ClienteNoEncontradoException extends RuntimeException{
    public ClienteNoEncontradoException(String mensaje){
        super(mensaje);
    }
}
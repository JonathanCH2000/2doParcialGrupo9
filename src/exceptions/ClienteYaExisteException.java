package exceptions;

public class ClienteYaExisteException extends RuntimeException{
    public ClienteYaExisteException(String mensaje){
        super(mensaje);
    }
}
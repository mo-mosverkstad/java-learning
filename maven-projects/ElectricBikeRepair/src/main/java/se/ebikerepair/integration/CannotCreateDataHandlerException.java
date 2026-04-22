package se.ebikerepair.integration;

public class CannotCreateDataHandlerException extends RuntimeException{
    public CannotCreateDataHandlerException(String dataHandlerClassName, Exception exception){
        super(String.format("Cannot create the data handler %s", dataHandlerClassName), exception);
    }
}

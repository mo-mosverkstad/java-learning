package se.ebikerepair.controller;

public class FailedOperationException extends Exception{
    public FailedOperationException(String message, Exception exception){
        super(message, exception);
    }
}

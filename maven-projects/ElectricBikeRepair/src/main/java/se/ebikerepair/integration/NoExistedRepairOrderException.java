package se.ebikerepair.integration;

public class NoExistedRepairOrderException extends Exception {
    public NoExistedRepairOrderException(String message) {
        super(message);
    }
}

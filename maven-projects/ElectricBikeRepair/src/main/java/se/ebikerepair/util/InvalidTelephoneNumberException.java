package se.ebikerepair.util;

public class InvalidTelephoneNumberException extends Exception {
    public InvalidTelephoneNumberException(String telephoneNumber){
        super(String.format("The phone number %s is not a valid phone number", telephoneNumber));
    }
}

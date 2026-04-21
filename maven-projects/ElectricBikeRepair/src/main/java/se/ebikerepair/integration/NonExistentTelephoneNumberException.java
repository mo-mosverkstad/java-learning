package se.ebikerepair.integration;

public class NonExistentTelephoneNumberException extends Exception {
    public NonExistentTelephoneNumberException(String telephoneNumber) {
        super(String.format("No customer found for telephone number: %s", telephoneNumber));
    }
}

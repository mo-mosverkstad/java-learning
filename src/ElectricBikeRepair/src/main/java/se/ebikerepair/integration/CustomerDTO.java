package se.ebikerepair.integration;

import java.util.List;

public class CustomerDTO {
    private final String name;
    private final String telephoneNumber;
    private final String emailAddress;
    private final List<BikeDTO> bikes;

    public CustomerDTO(String name, String telephoneNumber, String emailAddress, List<BikeDTO> bikes) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.emailAddress = emailAddress;
        this.bikes = bikes;
    }

    public String getName() {
        return name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public List<BikeDTO> getBikes() {
        return bikes;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "name='" + name + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}

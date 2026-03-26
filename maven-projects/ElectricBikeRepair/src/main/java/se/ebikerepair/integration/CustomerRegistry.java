package se.ebikerepair.integration;

import se.ebikerepair.util.JsonFileHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerRegistry{
    private final Map<String, CustomerDTO> customers = new HashMap<>();
    private final JsonFileHandler jsonFileHandler;

    public CustomerRegistry() {
        jsonFileHandler = new JsonFileHandler("customers.json");
        for (CustomerDTO customerDTO : jsonFileHandler.readList(CustomerDTO.class)){
            customers.put(customerDTO.getTelephoneNumber(), customerDTO);
        }
    }

    public CustomerDTO find(String telephoneNumber) {
        return customers.get(telephoneNumber);
    }

    public void save(CustomerDTO customer) {
        customers.put(customer.getTelephoneNumber(), customer);
        jsonFileHandler.writeList(new ArrayList<>(customers.values()));
    }
}

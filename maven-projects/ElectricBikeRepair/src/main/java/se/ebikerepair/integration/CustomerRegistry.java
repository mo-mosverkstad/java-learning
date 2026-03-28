package se.ebikerepair.integration;

import se.ebikerepair.util.JsonFileHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry for storing and retrieving customers. Loads initial data from customers.json.
 */
public class CustomerRegistry{
    private final Map<String, CustomerDTO> customers = new HashMap<>();
    private final JsonFileHandler jsonFileHandler;

    /**
     * Creates a customer registry and loads customers from the JSON resource file.
     */
    public CustomerRegistry() {
        jsonFileHandler = new JsonFileHandler("customers.json");
        for (CustomerDTO customerDTO : jsonFileHandler.readList(CustomerDTO.class)){
            customers.put(customerDTO.getTelephoneNumber(), customerDTO);
        }
    }

    /**
     * Finds a customer by telephone number.
     *
     * @param telephoneNumber the telephone number in E.164 format
     * @return the customer DTO, or null if not found
     */
    public CustomerDTO find(String telephoneNumber) {
        return customers.get(telephoneNumber);
    }

    /**
     * Saves a customer to the registry and persists to JSON.
     *
     * @param customer the customer to save
     */
    public void save(CustomerDTO customer) {
        customers.put(customer.getTelephoneNumber(), customer);
        jsonFileHandler.writeList(new ArrayList<>(customers.values()));
    }
}

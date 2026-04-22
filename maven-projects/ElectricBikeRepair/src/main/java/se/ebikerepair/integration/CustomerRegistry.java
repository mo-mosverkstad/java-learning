package se.ebikerepair.integration;

import se.ebikerepair.data.JsonFileHandler;
import se.ebikerepair.data.DatabaseHandler;
import se.ebikerepair.data.DataHandler;
import se.ebikerepair.data.DataHandlerFactory;
import se.ebikerepair.integration.CannotCreateDataHandlerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;

/**
 * Registry for storing and retrieving customers. Loads initial data from customers.json.
 */
public class CustomerRegistry{
    private final Map<String, CustomerDTO> customers = new HashMap<>();
    private final static String DATA_HANDLER_CLASS_NAME = "se.ebikerepair.data.JsonFileHandler";
    // private final static String DATA_HANDLER_CLASS_NAME = "se.ebikerepair.data.DatabaseHandler";
    private DataHandler dataHandler;

    /**
     * Creates a customer registry and loads customers from the JSON resource file.
     */
    public CustomerRegistry() {
        try{
            dataHandler = DataHandlerFactory.create(DATA_HANDLER_CLASS_NAME);
        }
        catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exception){
            throw new CannotCreateDataHandlerException(DATA_HANDLER_CLASS_NAME, exception);
        }
    }

    private void initRegistry() {
        if (dataHandler.isInitialized()) return;
        dataHandler.initDataHandler("customers");
        for (CustomerDTO customerDTO : dataHandler.readList(CustomerDTO.class)){
            customers.put(customerDTO.telephoneNumber(), customerDTO);
        }
    }

    /**
     * Finds a customer by telephone number.
     *
     * @param telephoneNumber the telephone number in E.164 format
     * @throws NonExistentTelephoneNumberException if the telephone number does not exist in the registry
     * @return the customer DTO
     */
    public CustomerDTO find(String telephoneNumber) throws NonExistentTelephoneNumberException {
        initRegistry();
        CustomerDTO customer = customers.get(telephoneNumber);
        if (customer == null){
            throw new NonExistentTelephoneNumberException(telephoneNumber);
        }
        return customer;
    }

    /**
     * Saves a customer to the registry and persists to JSON.
     *
     * @param customer the customer to save
     */
    public void save(CustomerDTO customer) {
        initRegistry();
        customers.put(customer.telephoneNumber(), customer);
        dataHandler.writeList(new ArrayList<>(customers.values()));
    }
}

package se.ebikerepair.data;

import java.util.List;

public class DatabaseHandler implements DataHandler {
    private String databaseName;

    public void initDataHandler(String resourceName){
        databaseName = resourceName;
        throw new NoDatabaseException(databaseName);
    }

    public boolean isInitialized(){
        return false;
    }

    public <T> List<T> readList(Class<T> clazz){
        throw new NoDatabaseException(databaseName);
    }

    public <T> void writeList(List<T> list){
        throw new NoDatabaseException(databaseName);
    }
}

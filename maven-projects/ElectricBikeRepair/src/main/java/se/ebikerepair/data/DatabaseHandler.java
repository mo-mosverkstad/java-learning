package se.ebikerepair.data;

import java.util.List;

// !!! Javadoc not written !!!
public class DatabaseHandler implements DataHandler {
    private String databaseName;

    // !!! Javadoc not written !!!
    public void initDataHandler(String resourceName){
        databaseName = resourceName;
        throw new NoDatabaseException(databaseName);
    }

    // !!! Javadoc not written !!!
    public boolean isInitialized(){
        return false;
    }

    // !!! Javadoc not written !!!
    public <T> List<T> readList(Class<T> clazz){
        throw new NoDatabaseException(databaseName);
    }

    // !!! Javadoc not written !!!
    public <T> void writeList(List<T> list){
        throw new NoDatabaseException(databaseName);
    }
}

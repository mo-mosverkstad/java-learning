package se.ebikerepair.data;

import java.util.List;

public interface DataHandler {
    public void initDataHandler(String resourceName);
    public boolean isInitialized();
    public <T> List<T> readList(Class<T> clazz);
    public <T> void writeList(List<T> list);
}

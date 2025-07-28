package DesignPatterns.StructuralPatterns.Proxy;

import java.util.HashMap;
import java.util.Map;

public class ProxyImages {
    private Map<String, Image> images = new HashMap<>();

    public void display(String filename) {
        Image image = images.get(filename);
        if (image == null) {
            image = new RealImage(filename);
            images.put(filename, image);
        }
        image.display();
    }
}

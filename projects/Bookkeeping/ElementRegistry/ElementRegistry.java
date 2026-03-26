package Bookkeeping.ElementRegistry;

import java.util.ArrayList;
import java.util.List;

import Bookkeeping.Element.Element;

public class ElementRegistry<T extends Element> {
    private final List<T> layouts = new ArrayList<>();
    public ElementRegistry(){}

    public T get(int index){
        return layouts.get(index);
    }

    public void append(T layout){
        layouts.add(layout);
    }

    public String toString(){
        StringBuilder content = new StringBuilder();
        content.append("ElementRegistry: [");
        for (int i = 0; i < layouts.size(); i++){
            content.append(layouts.get(i).getClass().getSimpleName()).append("[").append(i).append("]");
            content.append(", ");
        }
        content.append("/end");
        content.append("]");
        return content.toString();
    }
}

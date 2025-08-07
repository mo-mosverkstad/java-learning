package Bookkeeping.System;

import java.util.Stack;

import Bookkeeping.Element.Element;
import Bookkeeping.ElementCollection.ElementCollection;
import Bookkeeping.ElementRegistry.ElementRegistry;

public class BookkeepingController {
    private static final int ROOT_ELEMENT_INDEX = 0;
    private static final String ROOT_ELEMENT_NAME = "Root";

    private int currentIndex = ROOT_ELEMENT_INDEX;
    private final Stack<Integer> path = new Stack<>();
    private final ElementRegistry elementRegistry = new ElementRegistry();
    
    BookkeepingController(){
        path.add(ROOT_ELEMENT_INDEX);
        elementRegistry.append(new ElementCollection(ROOT_ELEMENT_NAME));
    }

    public Element getCurrent(){
        return elementRegistry.get(currentIndex);
    }

    public Element get(int index){
        return elementRegistry.get(index);
    }
}

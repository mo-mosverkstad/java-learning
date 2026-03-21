package Bookkeeping.System;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import Bookkeeping.Element.Element;
import Bookkeeping.ElementCollection.ElementCollection;
import Bookkeeping.ElementRegistry.ElementRegistry;
import Bookkeeping.Graphs.Graph;
import Bookkeeping.Tables.AbstractTable;

public class BookkeepingController {
    private static final String ROOT_LAYOUT_TYPE = "ElementCollection";
    private static final int ROOT_ELEMENT_INDEX = 0;
    private static final String ROOT_ELEMENT_NAME = "Root";

    private String currentLayoutType = ROOT_LAYOUT_TYPE;
    private int currentIndex = ROOT_ELEMENT_INDEX;
    private final Stack<Integer> path = new Stack<>() {{
        add(ROOT_ELEMENT_INDEX);
    }};
    private final Map<String, ElementRegistry<?>> elementRegistryMap = new HashMap<>(){{
        put("ElementCollection", new ElementRegistry<ElementCollection>(){{
            append(new ElementCollection(ROOT_ELEMENT_NAME));
        }});
        put("Table", new ElementRegistry<AbstractTable>());
        put("Graph", new ElementRegistry<Graph>());
    }};
    
    BookkeepingController(){
        
    }
}

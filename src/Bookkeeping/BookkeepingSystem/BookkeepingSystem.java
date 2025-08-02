package Bookkeeping.BookkeepingSystem;

import java.util.ArrayList;
import java.util.List;

import Bookkeeping.Layout.Layout;

public class BookkeepingSystem {
    private final List<Layout> layouts = new ArrayList<>();
    public BookkeepingSystem(){}

    public Layout get(int index){
        return layouts.get(index);
    }

    public void appendLayout(Layout layout){
        layouts.add(layout);
    }
}

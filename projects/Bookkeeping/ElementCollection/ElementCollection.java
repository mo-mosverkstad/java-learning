package Bookkeeping.ElementCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Bookkeeping.Element.Element;
import Bookkeeping.Utils.Pair;

public final class ElementCollection extends Element{
    private final List<Pair<String, Integer>> collectionList = new ArrayList<>();
    public ElementCollection(String name){
        super(name);
    }

    public void append(Pair<String, Integer> pair){
        collectionList.add(pair);
    }

    public int size(){
        return collectionList.size();
    }

    public Pair<String, Integer> get(int index){
        return collectionList.get(index);
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("----- ElementCollection(").append(name).append(") -----\n");
        if (size() == 0){
            stringBuilder.append("<Empty collection>");
        }
        else{
            for (Pair<String, Integer> collectionPair : collectionList){
                stringBuilder.append(collectionPair.toString());
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

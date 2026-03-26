package Sorting;

import java.util.ArrayList;

public class MyList<T extends Comparable<T>> extends ArrayList<T> {

    public MyList<T> append(T item) {
        super.add(item);
        return this;
    }

    public void sort() {
        for (int i = 0; i < this.size() - 1; i++) {
            for (int j = i+1; j < this.size(); j++) {
                if (this.get(i).compareTo(this.get(j)) > 0) {
                    System.out.println(this.get(i) + " - " + this.get(j));
                    T temp = this.get(j);
                    this.set(j, this.get(i));
                    this.set(i, temp);
                }
            }
        }
    }
}

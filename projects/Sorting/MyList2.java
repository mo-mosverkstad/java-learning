package Sorting;

import java.util.ArrayList;
import java.util.Comparator;

public class MyList2<T> extends ArrayList<T> {

    public MyList2<T> append(T item) {
        super.add(item);
        return this;
    }

    public void sortList(Comparator<T> comparator) {
        for (int i = 0; i < this.size() - 1; i++) {
            for (int j = i+1; j < this.size(); j++) {
                if (comparator.compare(this.get(i), this.get(j)) > 0) {
                    System.out.println(this.get(i) + " - " + this.get(j));
                    T temp = this.get(j);
                    this.set(j, this.get(i));
                    this.set(i, temp);
                }
            }
        }
    }
}

package Bookkeeping.Main;

import java.util.ArrayList;

import Bookkeeping.Tables.OrderedFlatTable;
import Bookkeeping.Utils.DataToolsTest;

public class Main {
    public static void main(String[] args) {
        OrderedFlatTable table = DataToolsTest.createTable();

        System.out.println(table);

        table.updateRow(2, new ArrayList<>() {{ add("Bob"); add(37); add(true); add("Dallas"); add(50000); add(20000); }});
        table.updateRow(20, new ArrayList<>() {{add("John"); add(25); add(true); add("New York"); add(50000); add(10000); }});
        table.updateRow(19, new ArrayList<>() {{add("Jane"); add(30); add(false); add("Los Angeles"); add(60000); add(20000); }});
        System.out.println("Successfully updated row 2 and 20 and 19");
        
        System.out.println(table);

        System.out.println(mapIndexUnordered(5, 3));
    }

    public static int mapIndexOrdered(int a, int b){
        // = (a + b - 1) * (a + b - 2) / 2 + a
        int sum = a + b;
        return (sum - 1)*(sum - 2)/2 + a;
    }

    public static int mapIndexUnordered(int a, int b){
        // = floor((a + b - 1)^2 / 4) + a
        if (a > b) {
            int temp = a;
            a = b;
            b = temp;
        }
        int inner = a + b - 1;
        int floorDiv = inner * inner / 4;
        return floorDiv + a;
    }
}

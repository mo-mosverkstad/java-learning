package DataTools;

import java.util.ArrayList;

public class DataTools {
    public static void main(String[] args) {
        Table table = DataToolsTest.createTable();

        table.updateRow(2, new ArrayList<>() {{ add(3); add("Bob"); add(37); add(true); add("Dallas"); add(50000); add(20000); }});
        table.updateRow(20, new ArrayList<>() {{ add(21); add("John"); add(25); add(true); add("New York"); add(50000); add(10000); }});
        table.updateRow(19, new ArrayList<>() {{ add(20); add("Jane"); add(30); add(false); add("Los Angeles"); add(60000); add(20000); }});
        // table.updateRow(18, new ArrayList<>() {{ add(10); add(false); add("other"); add(12); add(60000); add(20000); add(30000); }});

        
        System.out.println(table);
    }
}

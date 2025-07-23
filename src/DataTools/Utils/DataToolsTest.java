package DataTools.Utils;

import java.util.ArrayList;

import DataTools.Tables.BooleanColumn;
import DataTools.Tables.IntColumn;
import DataTools.Tables.StringColumn;
import DataTools.Tables.Table;

public class DataToolsTest {
    public static Table createTable(){
        Table table = new Table("Employees");
        table.addColumn(new IntColumn("id"));
        table.addColumn(new StringColumn("name"));
        table.addColumn(new IntColumn("age")); // age in years
        table.addColumn(new BooleanColumn("gender")); // true = male, false = female
        table.addColumn(new StringColumn("city"));
        table.addColumn(new IntColumn("salary")); // salary in dollars
        table.addColumn(new IntColumn("balance")); // balance in dollars


        table.appendRow(new ArrayList<>() {{ add(1); add("John"); add(25); add(true); add("New York"); add(50000); add(10000); }});
        table.appendRow(new ArrayList<>() {{ add(2); add("Jane"); add(30); add(false); add("Los Angeles"); add(60000); add(20000); }});
        table.appendRow(new ArrayList<>() {{ add(3); add("Bob"); add(35); add(true); add("Chicago"); add(70000); add(30000); }});
        table.appendRow(new ArrayList<>() {{ add(4); add("Alice"); add(40); add(false); add("Houston"); add(80000); add(40000); }});
        table.appendRow(new ArrayList<>() {{ add(5); add("Mike"); add(45); add(true); add("Phoenix"); add(90000); add(50000); }});
        table.appendRow(new ArrayList<>() {{ add(6); add("Emma"); add(28); add(false); add("Philadelphia"); add(52000); add(12000); }});
        table.appendRow(new ArrayList<>() {{ add(7); add("David"); add(33); add(true); add("San Antonio"); add(61000); add(18000); }});
        table.appendRow(new ArrayList<>() {{ add(8); add("Sophia"); add(29); add(false); add("San Diego"); add(58000); add(14000); }});
        table.appendRow(new ArrayList<>() {{ add(9); add("James"); add(38); add(true); add("Dallas"); add(75000); add(22000); }});
        table.appendRow(new ArrayList<>() {{ add(10); add("Olivia"); add(42); add(false); add("San Jose"); add(85000); add(27000); }});
        table.appendRow(new ArrayList<>() {{ add(11); add("Liam"); add(26); add(true); add("Austin"); add(49000); add(9000); }});
        table.appendRow(new ArrayList<>() {{ add(12); add("Mia"); add(31); add(false); add("Jacksonville"); add(63000); add(16000); }});
        table.appendRow(new ArrayList<>() {{ add(13); add("Noah"); add(37); add(true); add("Fort Worth"); add(70000); add(24000); }});
        table.appendRow(new ArrayList<>() {{ add(14); add("Isabella"); add(39); add(false); add("Columbus"); add(78000); add(26000); }});
        table.appendRow(new ArrayList<>() {{ add(15); add("Ethan"); add(43); add(true); add("Charlotte"); add(83000); add(35000); }});

        return table;
    }
}

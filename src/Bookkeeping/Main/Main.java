package Bookkeeping.Main;

import java.util.ArrayList;
import java.util.Arrays;

import Bookkeeping.Element.Element;
import Bookkeeping.ElementRegistry.ElementRegistry;
import Bookkeeping.Graphs.Graph;
import Bookkeeping.System.BookkeepingSystem;
import Bookkeeping.Tables.CollationEntry;
import Bookkeeping.Tables.CollationTypes;
import Bookkeeping.Tables.OrderedTable;
import Bookkeeping.Tables.UnorderedTable;
import Bookkeeping.Utils.DataToolsTest;

public class Main {
    public static ElementRegistry dataManager = new ElementRegistry();
    public static void main(String[] args) {
        /*
        orderedTableTest();
        unorderedTableTest();
        graphTest();
        */
        BookkeepingSystem bookkeepingSystem = new BookkeepingSystem();
        bookkeepingSystem.execute(bookkeepingSystem.VIEW_COMMAND);
    }

    public static void printSeparator(){
        System.out.println("=".repeat(64));
    }

    public static void orderedTableTest(){
        printSeparator();
        OrderedTable table = DataToolsTest.createTable();
        dataManager.append(table);

        System.out.println(table);

        table.updateRow(2, new ArrayList<>() {{ add("Bob"); add(37); add(true); add("Dallas"); add(50000); add(20000); }});
        table.updateRow(20, new ArrayList<>() {{add("John"); add(25); add(true); add("New York"); add(50000); add(10000); }});
        table.updateRow(19, new ArrayList<>() {{add("Jane"); add(30); add(false); add("Los Angeles"); add(60000); add(20000); }});
        System.out.println("Successfully updated row 2 and 20 and 19");
        
        System.out.println(table);

        Element table2 = dataManager.get(0);
        if (table2 instanceof OrderedTable obj){
            obj.appendRow(new ArrayList<>() {{add("John"); add(24); add(true); add("New York"); add(50000); add(10000); }});
        }
        
        System.out.println(table);
    }

    public static void unorderedTableTest(){
        printSeparator();
        UnorderedTable unorderedTable = new UnorderedTable("Technical reports");
        unorderedTable.addColumn(new CollationEntry("name", CollationTypes.STRING));
        unorderedTable.addColumn(new CollationEntry("importancy", CollationTypes.INTEGER));
        unorderedTable.addColumn(new CollationEntry("urgency", CollationTypes.INTEGER));
        unorderedTable.addColumn(new CollationEntry("priority", CollationTypes.INTEGER));

        
        
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("Network Security Audit", 8, 7, 9)));
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("Database Optimization", 6, 5, 6)));
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("Cloud Migration Plan", 9, 8, 10)));
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("Legacy System Review", 4, 3, 4)));
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("AI Integration Proposal", 7, 9, 8)));
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("Compliance Update", 5, 6, 5)));
        unorderedTable.appendRow(new ArrayList<>(Arrays.asList("Incident Response Report", 10, 10, 10)));

        System.out.println(unorderedTable);

        System.out.println("New TR is coming.");
        unorderedTable.insertRow(3, new ArrayList<>(Arrays.asList("RANS Security problem", 2, 3, 2)));
        System.out.println(unorderedTable);

        System.out.println("Updating some TR status");
        unorderedTable.updateRow(4, new ArrayList<>(Arrays.asList("Legacy System Review", 5, 1, 2)));
        System.out.println(unorderedTable);
    }

    public static void graphTest(){
        printSeparator();
        Graph graph = new Graph("My Graph");

        dataManager.append(graph);

        graph.updateVertex(0, "A");
        graph.updateVertex(1, "B");
        graph.updateVertex(2, "C");
        graph.updateVertex(3, "D");
        graph.updateVertex(4, "E");

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addBidirectionalEdge(3, 4);

        System.out.println(graph);

        graph.removeEdge(1, 3);

        System.out.println(graph);

        System.out.println(mapIndexUnordered(5, 3));
        System.out.println(mapIndexOrdered(5, 3));

        System.out.println(dataManager);
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

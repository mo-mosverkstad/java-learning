package DataTools;


public class DataTools {
    public static void main(String[] args) {
        Table table = DataToolsTest.createTable();

        table.updateRow(2, new Object[] { 3, "Bob", 37, true, "Dallas", 50000, 20000 });
        table.updateRow(20, new Object[] { 21, "John", 25, true, "New York", 50000, 10000 });
        table.updateRow(19, new Object[] { 20, "Jane", 30, false, "Los Angeles", 60000, 20000 });
        table.updateRow(18, new Object[] { 10, false, "other", 12, 60000, 20000, 30000 });
        
        System.out.println(table);
    }
}

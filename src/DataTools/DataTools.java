package DataTools;


public class DataTools {
    public static void main(String[] args) {
        Table table = new Table();
        table.addColumn("id", new IntColumn());
        table.addColumn("name", new StringColumn());
        table.addColumn("age", new IntColumn()); // age in years
        table.addColumn("gender", new BooleanColumn()); // true = male, false = female
        table.addColumn("city", new StringColumn());
        table.addColumn("salary", new IntColumn()); // salary in dollars
        table.addColumn("balance", new IntColumn()); // balance in dollars

        table.appendRow(new Object[] { 1, "John", 25, true, "New York", 50000, 10000 });
        table.appendRow(new Object[] { 2, "Jane", 30, false, "Los Angeles", 60000, 20000 });
        table.appendRow(new Object[] { 3, "Bob", 35, true, "Chicago", 70000, 30000 });
        
        System.out.println(table);

    }
}

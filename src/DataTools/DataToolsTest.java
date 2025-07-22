package DataTools;

public class DataToolsTest {
    public static Table createTable(){
        Table table = new Table();
        table.addColumn(new IntColumn("id"));
        table.addColumn(new StringColumn("name"));
        table.addColumn(new IntColumn("age")); // age in years
        table.addColumn(new BooleanColumn("gender")); // true = male, false = female
        table.addColumn(new StringColumn("city"));
        table.addColumn(new IntColumn("salary")); // salary in dollars
        table.addColumn(new IntColumn("balance")); // balance in dollars

        table.appendRow(new Object[] { 1, "John", 25, true, "New York", 50000, 10000 });
        table.appendRow(new Object[] { 2, "Jane", 30, false, "Los Angeles", 60000, 20000 });
        table.appendRow(new Object[] { 3, "Bob", 35, true, "Chicago", 70000, 30000 });
        table.appendRow(new Object[] { 4, "Alice", 40, false, "Houston", 80000, 40000 });
        table.appendRow(new Object[] { 5, "Mike", 45, true, "Phoenix", 90000, 50000 });
        table.appendRow(new Object[] { 6, "Emma", 28, false, "Philadelphia", 52000, 12000 });  // Female
        table.appendRow(new Object[] { 7, "David", 33, true, "San Antonio", 61000, 18000 });   // Male
        table.appendRow(new Object[] { 8, "Sophia", 29, false, "San Diego", 58000, 14000 });   // Female
        table.appendRow(new Object[] { 9, "James", 38, true, "Dallas", 75000, 22000 });        // Male
        table.appendRow(new Object[] { 10, "Olivia", 42, false, "San Jose", 85000, 27000 });   // Female
        table.appendRow(new Object[] { 11, "Liam", 26, true, "Austin", 49000, 9000 });         // Male
        table.appendRow(new Object[] { 12, "Mia", 31, false, "Jacksonville", 63000, 16000 });  // Female
        table.appendRow(new Object[] { 13, "Noah", 37, true, "Fort Worth", 70000, 24000 });    // Male
        table.appendRow(new Object[] { 14, "Isabella", 39, false, "Columbus", 78000, 26000 }); // Female
        table.appendRow(new Object[] { 15, "Ethan", 43, true, "Charlotte", 83000, 35000 });    // Male
        return table;
    }
}

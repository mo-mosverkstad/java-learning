package DataTools;

import java.util.Arrays;
import java.util.Scanner;

public class DataToolsCLI {
    public static final String PROMPT = "> ";
    public static final String COMMAND_EXIT = "exit";
    public static final String SPLIT_REGEX = "\\s+";
    public static void main(String[] args) {

        Table table = init();
        System.out.println("DataToolsCLI is running...");
        try (Scanner scanner = new Scanner(System.in)){
            boolean continueFlag = true;
            while (continueFlag) {
                System.out.print(PROMPT);
                String inputLine = scanner.nextLine();
                String[] inputLineTokens = inputLine.split(SPLIT_REGEX);
                if (inputLineTokens.length != 0 && inputLineTokens[0].trim() != "") {
                    String command = inputLineTokens[0];
                    String[] arguments = Arrays.copyOfRange(inputLineTokens, 1, inputLineTokens.length);
                    if (command.equals(COMMAND_EXIT)) {
                        continueFlag = false;
                    }
                    else if (command.equals("view")){
                        System.out.println(table);
                    }
                    else if (command.equals("u")){
                        table.updateRow(Integer.parseInt(arguments[0]) - 1, arguments);
                    }
                    else{
                        // System.out.println(inputLine);
                    }
                }
            }
            System.out.println("Bye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Table init(){
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
        table.appendRow(new Object[] { 15, "Ethan", 43, true, "Charlotte", 83000, 35000 }); 
        return table;
    }
}

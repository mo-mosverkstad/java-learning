package DataTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class DataToolsCLI {
    public static final String PROMPT = "> ";
    public static final String COMMAND_EXIT = "exit";
    public static final String SPLIT_REGEX = "\\s+";
    
    public static void main(String[] args) {
        Table table = DataToolsTest.createTable();

        Consumer<String[]> view = (arguments) -> {
            System.out.println(table);
        };
        Consumer<String[]> update = (arguments) -> {
            Class<?>[] columnTypes = table.getColumnTypes();
            List<Object> rowData = new ArrayList<>(columnTypes.length);
            for (int i = 0; i < arguments.length; i++) {
                Class<?> columnType = columnTypes[i];
                String argument = arguments[i];
                if (columnType == IntColumn.class){
                    if (Util.isStringDigit(argument)) {
                        rowData.add(Integer.parseInt(argument));
                    }
                    else{
                        System.out.println(String.format("PROBLEM: Malformed integer on column %d: %s", i, argument));
                    }
                }
                else if (columnType == BooleanColumn.class){
                    if (argument.equals("true")) {
                        rowData.add(true);
                    }
                    else if (argument.equals("false")) {
                        rowData.add(false);
                    }
                    else{
                        System.out.println(String.format("PROBLEM: Malformed boolean on column %d: %s", i, argument));
                    }
                }
                else if (columnType == StringColumn.class){
                    rowData.add(argument);
                }
                else{
                    System.out.println(String.format("PROBLEM: Unrecognized type on column %d: %s", i, argument));
                }
            }
            table.updateRow((int) rowData.get(0) - 1, rowData);
        };

        Map<String, Consumer<String[]>> commandMap = new HashMap<>();
        commandMap.put("view", view);
        commandMap.put("u", update);

        
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
                    else if (commandMap.containsKey(command)) {
                        commandMap.get(command).accept(arguments);
                    }
                    else{
                        System.out.println("Unknown command: " + command);
                    }
                }
            }
            System.out.println("Bye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

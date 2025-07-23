package DataTools.DataToolsCLI;

import java.util.ArrayList;
import java.util.List;

import DataTools.Tables.BooleanColumn;
import DataTools.Tables.IntColumn;
import DataTools.Tables.StringColumn;
import DataTools.Tables.Table;
import DataTools.Utils.Util;

public class UpdateCommand implements CommandInterface{
    private final Table table;
    public UpdateCommand(Table table){
        this.table = table;
    }

    @Override
    public void execute(String[] arguments){
        Class<?>[] columnTypes = table.getColumnTypes();
        List<Object> rowData = new ArrayList<>(columnTypes.length);
        if (columnTypes.length != arguments.length){
            System.out.println(String.format("PROBLEM: Numbers of arguments (%d) mismatch with table collation (%d)", arguments.length, columnTypes.length));
            return;
        }
        for (int i = 0; i < arguments.length; i++) {
            Class<?> columnType = columnTypes[i];
            String argument = arguments[i];
            if (columnType == IntColumn.class){
                if (Util.isStringDigit(argument)) {
                    rowData.add(Integer.parseInt(argument));
                }
                else{
                    System.out.println(String.format("PROBLEM: Malformed integer on argument %d: %s", i, argument));
                    return;
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
                    System.out.println(String.format("PROBLEM: Malformed boolean on argument %d: %s", i, argument));
                    return;
                }
            }
            else if (columnType == StringColumn.class){
                rowData.add(argument);
            }
            else{
                System.out.println(String.format("PROBLEM: Unrecognized type on argument %d: %s", i, argument));
                return;
            }
        }
        table.updateRow((int) rowData.get(0) - 1, rowData);
    }
}

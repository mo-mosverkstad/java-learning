package DataTools.DataToolsCLI;

import java.util.ArrayList;
import java.util.List;

import DataTools.Tables.BooleanColumn;
import DataTools.Tables.IntColumn;
import DataTools.Tables.StringColumn;
import DataTools.Tables.Table;
import DataTools.Utils.Util;

public final class UpdateCommand implements CommandInterface{
    private final static String BOOLEAN_KEYWORD_TRUE = "true";
    private final static String BOOLEAN_KEYWORD_FALSE = "false";

    private final static String MISMATCH_ARGUMENT_NUMBER_PROBLEM_FORMAT = "PROBLEM: Numbers of arguments (%d) mismatch with table collation columns (%d)";
    private final static String MALFORMED_INTEGER_PROBLEM_FORMAT = "PROBLEM: Malformed integer argument on argument %d: %s";
    private final static String MALFORMED_BOOLEAN_PROBLEM_FORMAT = "PROBLEM: Malformed boolean argument on argument %d: %s";
    private final static String UNRECOGNIZED_ARGUMENT_PROBLEM_FORMAT = "PROBLEM: Unrecognized argument on argument %d: %s";

    private final Table table;
    public UpdateCommand(Table table){
        this.table = table;
    }

    @Override
    public void execute(String[] arguments){
        Class<?>[] columnTypes = table.getColumnTypes();
        List<Object> rowData = new ArrayList<>(columnTypes.length);
        if (columnTypes.length != arguments.length){
            System.out.println(String.format(MISMATCH_ARGUMENT_NUMBER_PROBLEM_FORMAT, arguments.length, columnTypes.length));
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
                    System.out.println(String.format(MALFORMED_INTEGER_PROBLEM_FORMAT, i, argument));
                    return;
                }
            }
            else if (columnType == BooleanColumn.class){
                argument = argument.toLowerCase();
                if (argument.equals(BOOLEAN_KEYWORD_TRUE)) {
                    rowData.add(true);
                }
                else if (argument.equals(BOOLEAN_KEYWORD_FALSE)) {
                    rowData.add(false);
                }
                else{
                    System.out.println(String.format(MALFORMED_BOOLEAN_PROBLEM_FORMAT, i, argument));
                    return;
                }
            }
            else if (columnType == StringColumn.class){
                rowData.add(argument);
            }
            else{
                System.out.println(String.format(UNRECOGNIZED_ARGUMENT_PROBLEM_FORMAT, i, argument));
                return;
            }
        }
        table.updateRow((int) rowData.get(0) - 1, rowData);
    }
}

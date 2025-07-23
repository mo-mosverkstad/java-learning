package DataTools.Tables;

import java.util.List;
import java.util.ArrayList;


public class Table {
    private static final char NEWLINE_CHARACTER = '\n';

    private static final String NULL_KEYWORD = "null";
    private static final String SPACING_STRING = "  ";
    private static final String SEPARATOR_STRING = "-";
    private static final String EMPTY_TABLE_DISPLAY_FORMAT = "<Empty Table(%s)>";

    private static final String INVALID_ROW_DATA_ERROR = "Row data is not valid for this table";
    private static final String NEGATIVE_ROW_INDEX_ERROR = "Row index cannot be negative";


    private String name;
    private final List<ColumnInterface> columns = new ArrayList<>();
    public Table(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int rowSize(){
        return columns.get(0).size();
    }

    public int columnSize(){
        return columns.size();
    }

    public void addColumn(ColumnInterface column){
        columns.add(column);
    }

    public Class<?>[] getColumnTypes(){
        Class<?>[] columnClasses = new Class<?>[columns.size()];
        for (int i = 0; i < columnClasses.length; i++) {
            columnClasses[i] = columns.get(i).getClass();
        }
        return columnClasses;
    }

    public boolean isValidRow(List<Object> rowData){
        if (rowData.size() != columnSize()) {
            return false;
        }
        for (int i = 0; i < columnSize(); i++) {
            if (!columns.get(i).validate(rowData.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void appendRow(List<Object> rowData) throws IllegalArgumentException{
        if (!isValidRow(rowData)) {
            throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);
        }
        for (int i = 0; i < columnSize(); i++) {
            columns.get(i).append(rowData.get(i));
        }
    }

    public void updateRow(int index, List<Object> rowData) throws IllegalArgumentException{
        if (index < 0) {
            throw new IllegalArgumentException(NEGATIVE_ROW_INDEX_ERROR);
        }
        if (!isValidRow(rowData)) {
            throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);
        }
        for (int i = 0; i < columnSize(); i++) {
            ColumnInterface column = columns.get(i);
            while (index >= column.size()) {
                column.append();
            }
            column.set(index, rowData.get(i));
        }
    }

    /**
     * Returns a string representation of the table.
     *
     * @return         	A string representation of the table.
     */
    @Override
    public String toString() {
        if (columns.isEmpty()) return String.format(EMPTY_TABLE_DISPLAY_FORMAT, name);

        int rowCount = rowSize();
        int colCount = columnSize();

        // Calculate max width for each column (header + cell contents)
        int[] colWidths = new int[colCount];
        for (int j = 0; j < colCount; j++) {
            String header = columns.get(j).getName();
            colWidths[j] = header.length();
            for (int i = 0; i < rowCount; i++) {
                Object cell = columns.get(j).get(i);
                int len = (cell != null ? cell.toString() : NULL_KEYWORD).length();
                if (len > colWidths[j]) colWidths[j] = len;
            }
        }

        // Build the table string
        StringBuilder content = new StringBuilder();

        // Add table name header
        content.append("----- Table(").append(name).append(") -----\n");

        // Header
        for (int j = 0; j < colCount; j++) {
            content.append(pad(columns.get(j).getName(), colWidths[j])).append(SPACING_STRING);
        }
        content.append(NEWLINE_CHARACTER);

        // Separator
        for (int j = 0; j < colCount; j++) {
            content.append(SEPARATOR_STRING.repeat(colWidths[j])).append(SPACING_STRING);
        }
        content.append(NEWLINE_CHARACTER);

        // Rows
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Object cell = columns.get(j).get(i);
                content.append(pad(cell != null ? cell.toString() : NULL_KEYWORD, colWidths[j])).append(SPACING_STRING);
            }
            content.append(NEWLINE_CHARACTER);
        }

        return content.toString();
    }


    private String pad(String text, int width) {
        if (text == null) text = NULL_KEYWORD;
        return String.format("%-" + width + "s", text);
    }
}

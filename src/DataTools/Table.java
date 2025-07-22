package DataTools;

import java.util.List;
import java.util.ArrayList;

public class Table {
    private final List<ColumnInterface> columns = new ArrayList<>();
    public Table() {}

    public int rowSize(){
        return columns.get(0).size();
    }

    public int columnSize(){
        return columns.size();
    }

    public void addColumn(ColumnInterface column){
        columns.add(column);
    }

    public boolean isValidRow(Object[] rowData){
        if (rowData.length != columnSize()) {
            return false;
        }
        for (int i = 0; i < columnSize(); i++) {
            if (!columns.get(i).validate(rowData[i])) {
                return false;
            }
        }
        return true;
    }

    public void appendRow(Object[] rowData) throws IllegalArgumentException{
        if (!isValidRow(rowData)) {
            throw new IllegalArgumentException("Row data is not valid for this table");
        }
        for (int i = 0; i < columnSize(); i++) {
            columns.get(i).append(rowData[i]);
        }
    }

    public void updateRow(int index, Object[] rowData) throws IllegalArgumentException{
        if (index < 0) {
            throw new IllegalArgumentException("Row index cannot be negative");
        }
        if (!isValidRow(rowData)) {
            throw new IllegalArgumentException("Row data is not valid for this table");
        }
        for (int i = 0; i < columnSize(); i++) {
            ColumnInterface column = columns.get(i);
            while (index >= column.size()) {
                column.append();
            }
            column.set(index, rowData[i]);
        }
    }

    /**
     * Returns a string representation of the table.
     *
     * @return         	A string representation of the table.
     */
    @Override
    public String toString() {
        if (columns.isEmpty()) return "<Empty Table>";
        int rowCount = rowSize();
        int colCount = columnSize();

        // Calculate max width for each column (header + cell contents)
        int[] colWidths = new int[colCount];
        for (int j = 0; j < colCount; j++) {
            String header = columns.get(j).getName();
            colWidths[j] = header.length();
            for (int i = 0; i < rowCount; i++) {
                Object cell = columns.get(j).get(i);
                int len = (cell != null ? cell.toString() : "null").length();
                if (len > colWidths[j]) colWidths[j] = len;
            }
        }

        // Build the table string
        StringBuilder content = new StringBuilder();

        // Header
        for (int j = 0; j < colCount; j++) {
            content.append(pad(columns.get(j).getName(), colWidths[j])).append("  ");
        }
        content.append("\n");

        // Separator
        for (int j = 0; j < colCount; j++) {
            content.append("-".repeat(colWidths[j])).append("  ");
        }
        content.append("\n");

        // Rows
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                Object cell = columns.get(j).get(i);
                content.append(pad(cell != null ? cell.toString() : "null", colWidths[j])).append("  ");
            }
            content.append("\n");
        }

        return content.toString();
    }

    private String pad(String text, int width) {
        if (text == null) text = "null";
        return String.format("%-" + width + "s", text);
    }
}

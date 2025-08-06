package Bookkeeping.Tables;

import java.util.LinkedList;
import java.util.List;

public final class UnorderedTable extends AbstractTable{
    private static final String INVALID_ROW_DATA_ERROR = "Row data is not valid for this table";
    private static final String NEGATIVE_ROW_INDEX_ERROR = "Row index cannot be negative";
    private static final String ROW_INDEX_OF_BOUND_ERROR = "Row index is larger than the bound";

    private final List<List<Object>> rows = new LinkedList<>();

    public UnorderedTable(String name){
        super(name);
    }

    @Override
    protected List<List<Object>> getRows() {
        return rows;
    }

    @Override
    public void updateRow(int index, List<Object> rowData) throws IllegalArgumentException {
        if (index < 0) throw new IllegalArgumentException(NEGATIVE_ROW_INDEX_ERROR);
        if (index >= rows.size()) throw new IllegalArgumentException(ROW_INDEX_OF_BOUND_ERROR);
        if (!isValidRow(rowData)) throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);

        rows.set(index, rowData);
    }

    public void insertRow(int index, List<Object> rowData){
        if (index < 0) throw new IllegalArgumentException(NEGATIVE_ROW_INDEX_ERROR);
        if (index >= rows.size()) throw new IllegalArgumentException(ROW_INDEX_OF_BOUND_ERROR);
        if (!isValidRow(rowData)) throw new IllegalArgumentException(INVALID_ROW_DATA_ERROR);

        rows.add(index, rowData);
    }

    /*
    public void rearrange(int oldIndex, int newIndex){

    }
    */
}

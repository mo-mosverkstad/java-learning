package DataTools.DataToolsCLI;

import DataTools.Tables.Table;

public class ViewCommand implements CommandInterface{
    private final Table table;
    public ViewCommand(Table table){
        this.table = table;
    }

    @Override
    public void execute(String[] arguments){
        System.out.println(table);
    }
}

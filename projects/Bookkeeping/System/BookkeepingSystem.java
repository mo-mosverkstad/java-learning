package Bookkeeping.System;

public class BookkeepingSystem {
    // private static final Command myCommand = 

    private final BookkeepingController bookkeepingController = new BookkeepingController();
    public final Command VIEW_COMMAND = new ViewCommand(bookkeepingController);
    
    public BookkeepingSystem(){
        
    }

    public String execute(Command command){
        return command.execute();
    }
}
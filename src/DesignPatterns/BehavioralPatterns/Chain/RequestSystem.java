package DesignPatterns.BehavioralPatterns.Chain;

public class RequestSystem {

    private Level1SupportHandler level1SupportHandler;
    private Level2SupportHandler level2SupportHandler;
    private Level3SupportHandler level3SupportHandler;

    public RequestSystem() {
        level1SupportHandler = new Level1SupportHandler();
        level2SupportHandler = new Level2SupportHandler();
        level3SupportHandler = new Level3SupportHandler();
        level1SupportHandler.setNextHandler(level2SupportHandler);
        level2SupportHandler.setNextHandler(level3SupportHandler);
    }

    public boolean handleRequest(Request request) {
        return level1SupportHandler.handleRequest(request);
    }
}

package DesignPatterns.BehavioralPatterns.Chain;

public class SupportHandlerBase implements SupportHandler {
    private SupportHandler nextHandler;

    @Override
    public boolean handleRequest(Request request) {
        // handling will be done in the subclasses
        boolean result = realHandleRequest(request);
        if (result && nextHandler != null) {
            return nextHandler.handleRequest(request);
        }
        return result;
    }

    @Override
    public void setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected boolean realHandleRequest(Request request) {
        return true;
    }
}

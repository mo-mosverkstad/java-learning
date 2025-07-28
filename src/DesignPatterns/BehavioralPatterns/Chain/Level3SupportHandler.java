package DesignPatterns.BehavioralPatterns.Chain;

public class Level3SupportHandler extends SupportHandlerBase {
    @Override
    protected boolean realHandleRequest(Request request) {
        System.out.println(String.format("Level 3 support is handling this request (%s, %s)",
            request.getName(),
            request.getPriority().name()));
        return true;
    }
}

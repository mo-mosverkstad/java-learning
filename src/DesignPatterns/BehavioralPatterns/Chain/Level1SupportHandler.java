package DesignPatterns.BehavioralPatterns.Chain;

public class Level1SupportHandler extends SupportHandlerBase {

    @Override
    protected boolean realHandleRequest(Request request) {
        System.out.println(String.format("Level 1 support is handling this request (%s, %s)",
            request.getName(),
            request.getPriority().name()));
        return true;
    }
}

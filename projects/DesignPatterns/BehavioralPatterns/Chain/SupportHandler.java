package DesignPatterns.BehavioralPatterns.Chain;

public interface SupportHandler {
    boolean handleRequest(Request request);
    void setNextHandler(SupportHandler nextHandler);
}

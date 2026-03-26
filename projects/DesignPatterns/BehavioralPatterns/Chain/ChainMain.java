package DesignPatterns.BehavioralPatterns.Chain;

public class ChainMain {

    public static void main(String[] args) {
        RequestSystem requestSystem = new RequestSystem();
        System.out.println(requestSystem.handleRequest(new Request("Request 1", Priority.CRITICAL)));
        // requestSystem.handleRequest(new Request("Request 2", Priority.BASIC));
        // requestSystem.handleRequest(new Request("Request 3", Priority.INTERMEDIATE));
        // requestSystem.handleRequest(new Request("Request 4", Priority.BASIC));
        // requestSystem.handleRequest(new Request("Request 5", Priority.INTERMEDIATE));
        // requestSystem.handleRequest(new Request("Request 6", Priority.CRITICAL));
    }
}

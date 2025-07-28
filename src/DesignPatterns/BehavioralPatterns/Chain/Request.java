package DesignPatterns.BehavioralPatterns.Chain;

public class Request {
    private Priority priority;
    private String name;

    public Request(String name, Priority priority) {
        this.name = name;
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "priority=" + priority +
                ", name='" + name + '\'' +
                '}';
    }
}

package se.ebikerepair.inheritancecomposition;

public class MessageProtocol {
    private String title;
    private String description;

    public MessageProtocol(String title, String description){
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString(){
        return String.format("MessageProtocol(title = %s, description = %s)", title, description);
    }
}
